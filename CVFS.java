package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.*;
import java.util.List;
import java.util.Objects;

public class CVFS {
    private static VirtualFile _disk;
    private static VirtualFile _directory;
    private static int _diskSize;
    
    // ======= disk and navigation =======

    public static void diskNew(String diskName, int size) {
        _disk = new VirtualFile(diskName);
        _directory = _disk;
        _diskSize = 0;
        _disk.setMaxSize(size);
    }

    public static void diskSave(String path) {
        _disk.setSize(_diskSize);
        //todo save _disk and all children as .dat file i think
    }

    public static void diskLoad(String path) {
        //todo load dat file into _disk and load all children
        //replace virtual file with real loaded file at path
        VirtualFile tempDisk = new VirtualFile("disk");

        _disk = tempDisk;
        _directory = _disk;
        _diskSize = tempDisk.getSize();
    }

    public static void dirMove(String name) {
        if (name == null) {
            _directory = _directory.getParent();
        }
        else {
            List<VirtualFile> templist = _directory.listDir();
            //todo find matching name file of type dir in list, and set as _directory
            _directory = templist.get(0);
        }
    }

    // ======= File Methods =======

    //for directory
    public static void fileNew(String name) {
        //todo fail to add if already have same name file or directory
        _directory.fileAdd(new VirtualFile(name));
    }

    //for file
    public static void fileNew(String name, String type, String content) {
        //todo fail to add if already have same name file or directory
        int tempSize = VirtualFile.calculateSize(content) + _diskSize;
        //todo get rid of output below when hand in
        CLI.output("file size: " + tempSize + " | disk size: " +  _disk.getMaxSize());
        if (_disk.getMaxSize() > tempSize) {
            _diskSize = tempSize;
            _directory.fileAdd(new VirtualFile(name, type, content));
        } else {
            CLI.output("file too big, cant add to disk");
        }
    }

    public static void fileRename(String nameOld, String nameNew) {
        _directory.fileRename(nameOld, nameNew);
    }

    public static void fileDelete(String name) {
        _diskSize -= _directory.fileRemove(name);
    }

    // ======= view methods =======
    public static void list(boolean recursive, Criterion criterion) {
        for (VirtualFile file : _directory.listFile(recursive, criterion)) {
            if (!Objects.equals(file.getType(), "dir")){
                CLI.output("Name: " + file.getName() + " | Size: " + VirtualFile.calculateSize(file)+ " | Type: " + file.getType());
            }
        }
    }
}