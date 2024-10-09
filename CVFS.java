package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.*;
import java.util.ArrayList;
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
        VirtualFile tempFile;

        if (name == null)
            tempFile = _directory.getParent();
        else
            tempFile = _directory.getChild(name, false);

        if (tempFile != null)
            _directory = tempFile;
        else
            if (name == null)
                CLI.output("current directory is already disk root.");
            else
                CLI.output("directory not found.");
    }

    // ======= File Methods =======

    //for directory
    public static void fileNew(String name) {
        if (_directory.getChild(name, false) == null)
            _directory.fileAdd(new VirtualFile(name));
        else
            CLI.output("directory with the same name already exists");
    }

    //for file
    public static void fileNew(String name, String type, String content) {
        if (_directory.getChild(name, true) == null) {
            int tempSize = VirtualFile.calculateSize(content) + _diskSize;
            if (_disk.getMaxSize() > tempSize) {
                _diskSize = tempSize;
                _directory.fileAdd(new VirtualFile(name, type, content));
            }
            else
                CLI.output("file too big, cant add to disk");
        }
        else
            CLI.output("file with the same name already exists");
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

    // ====== path methods =======
    public static String getPath() {
        // Check if the disk is created
        if (_disk == null) {
            CLI.output("No disk created.");
            return "";
        }

        StringBuilder path = new StringBuilder("$:");
        String currentDir = _directory.getName();

        // Build the path upwards to the root
        VirtualFile parent = _directory.getParent();
        while (parent != null) {
            path.insert(2, parent.getName() + ":");
            parent = parent.getParent();
        }

        // If we are in the root directory, just return the current directory
        if (path.length() == 2) {
            path.append(currentDir);
        } else {
            path.append(currentDir); // Append current directory to the path
        }

        return path.toString();
    }

    public static List<String> parsePath(String path) {
        if (!path.startsWith("$:")) {
            CLI.output("Invalid path format. Path should start with '$:'.");
            return null;
        }

        String[] names = path.substring(2).split(":");
        List<String> validFiles = new ArrayList<>();
        VirtualFile current = _directory;

        for (String name : names) {
            VirtualFile child = current.getChild(name, true);
            if (child != null) {
                validFiles.add(child.getName());
                current = child; // Move to the child directory or file
            } else {
                CLI.output("File or directory '" + name + "' not found in current path.");
                return null; // Return null if any part of the path is invalid
            }
        }

        return validFiles; // Return the list of valid file names
    }
}