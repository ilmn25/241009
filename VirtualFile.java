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
    private long _maxSize;
    private long _size;

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

    public static long getSize(VirtualFile file) {
        return 40 + (file.getContent().length() * 2L);
    }

    public static long getSize(String content) {
        return 40 + (content.length() * 2L);
    }

    public long getSize() {
        return _size;
    }

    public void setSize(long size) {
        _size = size;
    }

    public Long getMaxSize() {
        return _maxSize;
    }

    public void setMaxSize(long maxSize) {
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
        if (Objects.equals(_type, "dir")) {
            file.setParent(this);
            _children.add(file);
            CLI.output("Added file: " + file.getName() + " to directory: " + _name);
        } else {
            CLI.output("Cannot add file to a non-directory type: " + _name);
        }
    }

    public void fileRemove(String name) {
        if (Objects.equals(_type, "dir")) {
            for (VirtualFile child : _children) {
                if (child.getName().equals(name)) {
                    _children.remove(child);
                    CLI.output("Removed file: " + name + " from directory: " + _name);
                    return;
                }
            }
            CLI.output("File not found: " + name + " in directory: " + _name);
        } else {
            CLI.output("Cannot remove file from a non-directory type: " + _name);
        }
    }

    public void fileRename(String nameOld, String nameNew) {
        if (Objects.equals(_type, "dir")) {
            for (VirtualFile child : _children) {
                if (child.getName().equals(nameOld)) {
                    child.setName(nameNew);
                    CLI.output("Renamed file: " + nameOld + " from directory: " + _name);
                    return;
                }
            }
            CLI.output("File not found: " + nameOld + " in directory: " + _name);
        } else {
            CLI.output("Cannot remove file from a non-directory type: " + _name);
        }
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