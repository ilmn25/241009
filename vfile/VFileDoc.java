package hk.edu.polyu.comp.comp2021.cvfs.model.vfile;

import hk.edu.polyu.comp.comp2021.cvfs.model.CLI;
import hk.edu.polyu.comp.comp2021.cvfs.model.CVFS;

import java.io.Serializable;

/**
 * subclass for doc objects
 */
public class VFileDoc extends VFile implements Serializable {

    private String _content;

    /**
     * @param name of instance
     * @param type of doc
     * @param content of doc
     */
    public VFileDoc(String name, String type, String content) {
        super(name, type);
        this._content = content;
        CLI.output("created file: " + name + "." + type);
    }

    /**
     * @return content string
     */
    public String getContent() {
        if (_content == null) return "";
        return _content;
    }


    /**
     * @return size of this file
     */
    @Override
    public int getSize() {
        return CVFS.BASE_SIZE + (getContent().length() * 2);
    }
}
