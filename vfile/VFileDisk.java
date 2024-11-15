package hk.edu.polyu.comp.comp2021.cvfs.model.vfile;

import java.io.Serializable;

/**
 * subclass for disk object
 */
public class VFileDisk extends VFileDir implements Serializable {

    private final int _maxSize;
    private int _size;

    /**
     * @param maxSize size of disk
     */
    public VFileDisk(int maxSize) {
        super("disk");
        this._maxSize = maxSize;
        this._size = 0;
    }

    /**
     * @return get size
     */
    @Override
    public int getSize() {
        return _size;
    }

    /**
     * @param size set
     */
    public void setSize(int size) {
        this._size = size;
    }

    /**
     * @return get max size limit
     */
    public int getMaxSize() {
        return _maxSize;
    }

}
