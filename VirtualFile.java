package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VirtualFile {

    // =========================== object specific variables
    private String _name;
    private String _type; // "dir", "txt", "java", "html", "css"
    private String _content;
    private List<VirtualFile> _children;
    private VirtualFile _parent;

    // for disk only
    private int _maxSize;
    private int _size;

    // ========================== create file object

    //file
    public VirtualFile(String name, String type, String content) {
        _name = name;
        _type = type;
        _content = content;

        CLI.output("Created file: " + name + "." + type);
    }

    //directory or disk
    public VirtualFile(String name) {
        _name = name;
        _type = "dir";
        _children = new ArrayList<>();

        CLI.output("Created dir: " + name);
    }







    //----------- helper methods

    public static int getSize(VirtualFile file) {
        return 40 + (file.getContent().length() * 2);
    }

    public static int getSize(String content) {
        return 40 + (content.length() * 2);
    }

    public int getSize() {
        return _size;
    }

    public void setSize(int size) {
        _size = size;
    }

    public int getMaxSize() {
        return _maxSize;
    }

    public void setMaxSize(int maxSize) {
        _maxSize = maxSize;
    }

    public String getName() {
        return _name;
    }

    public String getContent() {
        if (_content == null) return "";
        return _content;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getType() {
        return _type;
    }

    public VirtualFile getParent() {
        return _parent;
    }

    public void setParent(VirtualFile parent) {
        _parent = parent;
    }












    // ----------- dir methods
    public void fileAdd(VirtualFile file) {
        file.setParent(this);
        _children.add(file);
        CLI.output("Added file: " + file.getName() + " to directory: " + _name);
    }

    public int fileRemove(String name) {
        for (VirtualFile file : _children) {
            if (file.getName().equals(name)) {
                _children.remove(file);
                CLI.output("Removed file: " + name + " from directory: " + _name);
                return VirtualFile.getSize(file);
            }
        }
        CLI.output("File not found: " + name + " in directory: " + _name);
        return 0;
    }

    public void fileRename(String nameOld, String nameNew) {
        for (VirtualFile child : _children) {
            if (child.getName().equals(nameOld)) {
                child.setName(nameNew);
                CLI.output("Renamed file: " + nameOld + " from directory: " + _name);
                return;
            }
        }
        CLI.output("File not found: " + nameOld + " in directory: " + _name);
    }















    public List<VirtualFile> list(boolean recursive) {
        if (!recursive) {
            return new ArrayList<>(_children);
        }
        else {
            //todo add recursion
            return null;
        }
    }

    public List<VirtualFile> list(boolean recursive, Criterion criterion) {
        List<VirtualFile> tempList = new ArrayList<>();
        if (!recursive) {
            for (VirtualFile file : _children) {
                if (criterion.checkFile(file)) {
                    tempList.add(file);
                }
            }
        }
        else {
            //todo add recursion
        }
        return tempList;
    }
}