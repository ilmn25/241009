package hk.edu.polyu.comp.comp2021.cvfs.model;

import hk.edu.polyu.comp.comp2021.cvfs.model.vfile.VFileDir;
import hk.edu.polyu.comp.comp2021.cvfs.model.vfile.VFileDisk;
import hk.edu.polyu.comp.comp2021.cvfs.model.vfile.VFileDoc;
import hk.edu.polyu.comp.comp2021.cvfs.model.vfile.VFile;

/**
 * main file system code that handle what to do and call, backend
 */
public class CVFS {
    /**
     * size multiplier from pdf because magic number error in code check
     */
    public static final int BASE_SIZE = 40;
    private static VFileDisk _disk;
    private static VFileDir _directory;


    /**
     * @return get disk pointer
     */
    public static VFileDisk getDisk() {
        return _disk;
    }

    /**
     * @return get dir pointer
     */
    public static VFileDir getDir() {
        return _directory;
    }

    /**
     * @param disk load this disk and reset dir to root
     */
    public static void setDisk(VFileDisk disk) {
        _disk = disk;
        _directory = _disk;
    }

    /**
     * @return check if disk is not made yet
     */
    // ======= disk and navigation =======
    public static boolean hasDisk() {
        if (_disk != null) return true;
        CLI.output("command error: please create a disk");
        return false;
    }

    /**
     * @param size size of disk to make
     */
    public static void diskNew(int size) {
        _disk = new VFileDisk(size);
        _directory = _disk;
    }

    /**
     * @param name name of child dir to move to
     */
    public static void dirMove(String name) {
        VFile tempFile = _directory.getChild(name, "dir");
        if (tempFile != null) {
            CLI.output("directory moved to " + name);
            _directory = (VFileDir) tempFile;
        } else CLI.output("directory not found");
    }

    // ======= File Methods =======

    /**
     * @param name name of dir to make
     */
    public static void dirNew(String name) {
        if (_directory.getChild(name, "dir") == null)
            _directory.fileAdd(new VFileDir(name));
        else
            CLI.output("directory with the same name already exists");
    }

    /**
     * @param name    name of new doc
     * @param type    type of new doc
     * @param content content of doc
     */
    public static void docNew(String name, String type, String content) {
        if (_directory.getChild(name, type) == null) {
            int tempSize = BASE_SIZE + (content.length() * 2) + _disk.getSize();
            if (_disk.getMaxSize() > tempSize) {
                _disk.setSize(tempSize);
                _directory.fileAdd(new VFileDoc(name, type, content));
            } else
                CLI.output("size error: file too big, can't add to disk");
        } else
            CLI.output("file with the same name already exists");
    }

    /**
     * @param nameOld name to find
     * @param nameNew name to change to
     */
    public static void fileRename(String nameOld, String nameNew) {
        _directory.fileRename(nameOld, nameNew);
    }

    /**
     * @param name find and delete , fileremove returns size
     */
    public static void fileDelete(String name) {
        _disk.setSize(_disk.getSize() - _directory.fileRemove(name));
    }

}
