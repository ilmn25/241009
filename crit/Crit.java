package hk.edu.polyu.comp.comp2021.cvfs.model.crit;

import hk.edu.polyu.comp.comp2021.cvfs.model.CLI;
import hk.edu.polyu.comp.comp2021.cvfs.model.vfile.VFile;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * criterion superclass
 */
public abstract class Crit implements Serializable {

    /**
     * key value map of every criterion made that is loaded
     */
    protected static Map<String, Crit> _map = new HashMap<>(); // To store all criteria

    static {new CritD();};

    /**
     * @param name name of criterion to get from map
     * @return criterion found or null
     */
    public static Crit getCriterion(String name) {
        return _map.get(name);
    }

    /**
     * @return pointer to map
     */
    public static Map<String, Crit> getCriterionMap() {
        return _map;
    }

    /**
     * @param map set map to this map loaded from dat
     */
    public static void setCriterionMap(Map<String, Crit> map) {
        _map = map;
    }

    /**
     * list all crit in map
     */
    public static void list() {
        CLI.output("All defined criteria");
        for (Crit criterion : _map.values()) {
            CLI.output("-----------------------------");
            CLI.output(criterion.toString());
        }
    }

    /**
     * name of crit instance
     */
    protected String _name;
    /**
     * type of crit instance
     */
    protected String _type;

    /**
     * @return type of instance
     */
    public String getType() {
        return _type;
    }

    /**
     * @return name of instance
     */
    public String getName() {
        return _name;
    }

    /**
     * @param file file to check
     * @return result from the subclasses has conditions written, this relays over to subclass
     */
    public abstract boolean checkFile(VFile file);

    @Override
    public abstract String toString();
}
