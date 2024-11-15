package hk.edu.polyu.comp.comp2021.cvfs.model.crit;

import hk.edu.polyu.comp.comp2021.cvfs.model.CLI;
import hk.edu.polyu.comp.comp2021.cvfs.model.vfile.VFileDoc;
import hk.edu.polyu.comp.comp2021.cvfs.model.vfile.VFile;

/**
 * simple criterion
 */
public class CritS extends Crit {
    private String _attribute;
    private String _operator;
    private String _value;

    /**
     * @param name name of instance
     * @param attribute what to process, name size type
     * @param operator operator
     * @param value value
     */
    public CritS(String name, String attribute, String operator, String value) {
        _name = name;
        _type = "simple";
        _attribute = attribute;
        _operator = operator;
        _value = value;
        _map.put(name, this);
        CLI.output("Created simple criterion: " + name);
    }

    @Override
    public boolean checkFile(VFile file) {
        switch (_attribute) {
            case "name":
                return file.getName().contains(_value);
            case "type":
                return file.getType().equals(_value);
            case "size":
                int fileSize = file.getSize();
                int criterionValue = Integer.parseInt(_value);
                switch (_operator) {
                    case ">":
                        return fileSize > criterionValue;
                    case "<":
                        return fileSize < criterionValue;
                    case ">=":
                        return fileSize >= criterionValue;
                    case "<=":
                        return fileSize <= criterionValue;
                    case "==":
                        return fileSize == criterionValue;
                    case "!=":
                        return fileSize != criterionValue;
                }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Criterion Name: " + _name + "\n" +
                "Type: " + _type + "\n" +
                "Attribute: " + _attribute + "\n" +
                "Operator: " + _operator + "\n" +
                "Value: " + _value;
    }
}
