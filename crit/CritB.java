package hk.edu.polyu.comp.comp2021.cvfs.model.crit;

import hk.edu.polyu.comp.comp2021.cvfs.model.CLI;
import hk.edu.polyu.comp.comp2021.cvfs.model.vfile.VFile;

import java.util.Objects;

/**
 * binary criterion
 */
public class CritB extends Crit {

    private Crit _criterionA;
    private Crit _criterionB;
    private String _operator;

    /**
     * @param name name of instance
     * @param criterionA argument a
     * @param criterionB argument b
     * @param operator operator and or
     */
    public CritB(String name, Crit criterionA, Crit criterionB, String operator) {
        _name = name;
        _type = "binary";
        _criterionA = criterionA;
        _criterionB = criterionB;
        _operator = operator;
        _map.put(name, this);
        CLI.output("Created binary criterion: " + name);
    }

    @Override
    public boolean checkFile(VFile file) {
        if (Objects.equals(_operator, "&&"))
            return _criterionA.checkFile(file) && _criterionB.checkFile(file);
        else
            return _criterionA.checkFile(file) || _criterionB.checkFile(file);
    }

    @Override
    public String toString() {
        return "Criterion Name: " + _name + "\n" +
                "Type: " + _type + "\n" +
                "First criteria: " + _criterionA.getName() + "\n" +
                "Operator: " + _operator + "\n" +
                "Second criteria: " + _criterionB.getName();
    }
}
