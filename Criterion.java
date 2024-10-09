package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Criterion {

    //     ============== static methods

    private static Map<String, Criterion> _map = new HashMap<>(); // To store all criteria

    public static Criterion getCriterion(String name) {
        return _map.get(name);
    }

    public static void list() {
        CLI.output("All defined criteria:");
        for (Criterion criterion : _map.values()) {
            CLI.output("-----------------------------");
            if (Objects.equals(criterion._name, "isDocument")) {
                CLI.output("Criterion Name: IsDocument \n Checks if file is a document");
            }
            else {
                String output = "Criterion Name: " + criterion._name + "\n" +
                        "Type: " + criterion._type;

                switch (criterion._type) {
                    case "simple": // Simple criterion
                        output += "Attribute: " + criterion._attribute + "\n" +
                                "Operator: " + criterion._operator + "\n" +
                                "Value: " + criterion._value;
                        break;
                    case "negation": // Negation
                        output += "Negation of: " + criterion._criterionA._name;
                        break;
                    case "binary": // Binary
                        output += "Combination of: " +
                                criterion._criterionA._name + " and " +
                                criterion._criterionB._name;
                        break;
                    default:
                        output += "Unknown criterion type.";
                        break;
                }

                CLI.output(output);
            }
        }
    }

    //    =========================== object

    private String _name;
    private String _type; // "simple", "negation", "binary", "document"

    // If attrName is name, op must be contains and val must be a string
    // If attrName is type, op must be equals and val must be a string in the
    // If attrName is size, op can be >, <, >=, <=, ==, or !=, and val must be an integer.
    private String _attribute; // name type size
    private String _operator;
    private String _value;

    private Criterion _criterionA;
    private Criterion _criterionB;


    public String getType()
    {
        return _type;
    }
    public String getAttribute()
    {
        return _attribute;
    }
    public String getOperator()
    {
        return _operator;
    }
    public String getValue()
    {
        return _value;
    }
    public Criterion getA()
    {
        return _criterionA;
    }
    public Criterion getB()
    {
        return _criterionB;
    }


    // ===== check logic ======
    public boolean checkFile(VirtualFile file) {
        // Check if file fits criteria based on _type
        switch (_type) {
            case "simple":
                return checkSimple(file);
            case "negation":
                return checkNegation(file);
            case "binary":
                return checkBinary(file);
            case "document":
                return checkDocument(file);
        }
        CLI.output("error, type in checkfile not found");
        return false;
    }

    private boolean checkSimple(VirtualFile file) {
        // TODO Implement criteria for simple check
        return true;
    }

    private boolean checkNegation(VirtualFile file) {
        return !_criterionA.checkFile(file);
    }

    private boolean checkBinary(VirtualFile file) {
        if (Objects.equals(_operator, "&&"))
            return _criterionA.checkFile(file) && _criterionB.checkFile(file);
        else
            return _criterionA.checkFile(file) || _criterionB.checkFile(file);
    }

    private boolean checkDocument(VirtualFile file) {
        return !Objects.equals(file.getType(), "dir");
    }

// ========================== create criterion object

    // make isDocument on wake (task #10)
    static { new Criterion();}
    public Criterion() {
        _name = "IsDocument";
        _type = "document";
        _map.put(_name, this);
    }

    // simple: create custom criterion
    public Criterion(String name, String attribute, String operator, String value) {
        _name = name;
        _type = "simple";

        _attribute = attribute;
        _operator = operator;
        _value = value;

        _map.put(name, this);
        CLI.output("Created simple criterion: " + name);
    }

    // negation: create opposite of existing criterion nameA
    public Criterion(String name, Criterion criterionA) {
        _name = name;
        _type = "negation";

        _criterionA = criterionA;

        _map.put(name, this);
        CLI.output("Created negation criterion: " + name);
    }

    // binary: combine two criteria
    public Criterion(String name, Criterion criterionA, Criterion criterionB, String operator) {
        _name = name;
        _type = "binary";

        _operator = operator;
        _criterionA = criterionA;
        _criterionB = criterionB;

        _map.put(name, this);
        CLI.output("Created binary criterion: " + name);
    }
}