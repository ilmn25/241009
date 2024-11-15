package hk.edu.polyu.comp.comp2021.cvfs.model.vfile;

import hk.edu.polyu.comp.comp2021.cvfs.model.CLI;
import hk.edu.polyu.comp.comp2021.cvfs.model.crit.Crit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * subclass for directory objects
 */
public class VFileDir extends VFile implements Serializable {

    private List<VFile> _children;

    /**
     * @param name name of directory
     */
    public VFileDir(String name) {
        super(name, "dir");
        this._children = new ArrayList<>();
        CLI.output("created directory " + name);
    }

    /**
     * @param file pointer to add to children
     */
    public void fileAdd(VFile file) {
        _children.add(file);
        CLI.output("added file " + file.getName() + " of type " + file.getType() + " to " + getName());
    }

    /**
     * @param name of child to remove one of
     * @return size of child removed
     */
    public int fileRemove(String name) {
        for (VFile child : _children) {
            if (child.getName().equals(name)) {
                _children.remove(child);
                CLI.output("removed file: " + name + " of type " + child.getType() + " from " + getName());
                return child.getSize();
            }
        }
        CLI.output("file not found: " + name + " in " + getName());
        return 0;
    }

    /**
     * @param nameOld name of file to rename
     * @param nameNew new name
     */
    public void fileRename(String nameOld, String nameNew) {
        for (VFile child : _children) {
            if (child.getName().equals(nameOld)) {
                child.setName(nameNew);
                CLI.output("renamed file: " + nameOld + " of type " + child.getType() + " from " + getName());
                return;
            }
        }
        CLI.output("File not found: " + nameOld + " in " + getName());
    }

    /**
     * @param name to find
     * @param type to find
     * @return pointer of child
     */
    public VFile getChild(String name, String type) {
        for (VFile file : _children) {
            if (file.getName().equals(name) && file.getType().equals(type)) {
                return file;
            }
        }
        return null;
    }

    /**
     * @param recursive list subfiles or not
     * @param criterion condition to show, null of none
     */
    public void list(boolean recursive, Crit criterion) {
        CLI.output("Name: " + getName() + " | Size: " + (String) listRecursive( recursive, criterion, 1)[0]);
    }

    /**
     * @param recursive list subfiles or not
     * @param criterion condition to show, null of none
     * @param depth depth away from current dir for indent
     * @return string visualization of files and size of whole dir
     */
    public Object[] listRecursive(boolean recursive, Crit criterion, int depth) {
        int tempSize = 0;
        Object[] tempObj;
        String output = "\n";

        for (VFile file : _children) {
            if (!Objects.equals(file.getType(), "dir")) {
                if (criterion == null || criterion.checkFile(file)) {
                    tempSize += file.getSize();
                    output += ("      ".repeat(depth) + "Name: " + file.getName() + " | Size: " + file.getType() + " | Size: " + file.getSize() + "\n");
                }
            } else if (recursive) {
                output += ("      ".repeat(depth) + "Name: " + file.getName() + " | Size: ");
                tempObj = ((VFileDir) file).listRecursive(true, criterion, depth + 1);
                output += (String) tempObj[0];
                tempSize += (int) tempObj[1];
            }
        }
        return new Object[]{tempSize + output, tempSize};
    }

    @Override
    public int getSize() {
        int tempSize = 0;
        for (VFile file : _children) {
            tempSize += file.getSize();
        }
        return tempSize;
    }
}
