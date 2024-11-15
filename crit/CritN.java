package hk.edu.polyu.comp.comp2021.cvfs.model.crit;

import hk.edu.polyu.comp.comp2021.cvfs.model.CLI;
import hk.edu.polyu.comp.comp2021.cvfs.model.vfile.VFile;

/**
 * negation criterion
 */
public class CritN extends Crit {

    private Crit _criterionA;

    /**
     * @param name name of instance
     * @param criterionA crit to negate, aka opposite of
     */
    public CritN(String name, Crit criterionA) {
        _name = name;
        _type = "negation";
        _criterionA = criterionA;
        _map.put(name, this);
        CLI.output("Created negation criterion: " + name);
    }

    @Override
    public boolean checkFile(VFile file) {
        return !_criterionA.checkFile(file);
    }

    @Override
    public String toString() {
        return "Criterion Name: " + _name + "\n" +
                "Type: " + _type + "\n" +
                "Negated criteria: " + _criterionA.getName();
    }
}
