package hk.edu.polyu.comp.comp2021.cvfs.model.vfile;

import java.io.Serializable;

/**
 * virtual file superclass
 */
public abstract class VFile implements Serializable {

    private String _name;
    private final String _type;

    /**
     * @param name name of instance
     * @param type type of instance
     */
    public VFile(String name, String type) {
        this._name = name;
        this._type = type;
    }

    /**
     * @return name get
     */
    public String getName() {
        return _name;
    }

    /**
     * @param name set
     */
    public void setName(String name) {
        this._name = name;
    }

    /**
     * @return type get
     */
    public String getType() {
        return _type;
    }

    /**
     * @return size, written in subclass
     */
    public abstract int getSize();
}
