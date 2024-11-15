package hk.edu.polyu.comp.comp2021.cvfs.model.crit;

import hk.edu.polyu.comp.comp2021.cvfs.model.vfile.VFile;

import java.util.Objects;

/**
 * custom criterion isdocument
 */
public class CritD extends Crit {

    /**
     * custom criterion isdocument
     */
    public CritD() {
        _name = "IsDocument";
        _type = "document";
        _map.put(_name, this);
    }

    @Override
    public boolean checkFile(VFile file) {
        return !Objects.equals(file.getType(), "dir");
    }

    @Override
    public String toString() {
        return "Criterion Name: IsDocument\nChecks if file is a document";
    }
}
