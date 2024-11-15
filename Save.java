package hk.edu.polyu.comp.comp2021.cvfs.model;

import hk.edu.polyu.comp.comp2021.cvfs.model.vfile.VFileDisk;
import hk.edu.polyu.comp.comp2021.cvfs.model.crit.Crit;

import java.io.*;
import java.util.Map;

/**
 * save load file system
 */
public class Save implements Serializable {

    /**
     * @param path save to path
     */
    public static void diskSave(String path) {
        Save data = new Save(CVFS.getDisk(), Crit.getCriterionMap());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(data);
            CLI.output("disk and criteria saved to " + path);
        } catch (IOException e) {
            CLI.output("error saving disk: " + e.getMessage());
        }
    }

    /**
     * @param path load from path
     */
    public static void diskLoad(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            Save data = (Save) ois.readObject();
            CVFS.setDisk(data.getDisk());
            Crit.setCriterionMap(data.getCriteriaMap());

            CLI.output("disk and criteria loaded from " + path);
        } catch (IOException | ClassNotFoundException e) {
            CLI.output("error loading disk: " + e.getMessage());
        }
    }

    private VFileDisk disk;
    private Map<String, Crit> criteriaMap;

    /**
     * @param disk disk class to save or laod
     * @param criteriaMap map class to save or load
     */
    public Save(VFileDisk disk, Map<String, Crit> criteriaMap) {
        this.disk = disk;
        this.criteriaMap = criteriaMap;
    }

    /**
     * @return get disk from class
     */
    public VFileDisk getDisk() {
        return disk;
    }

    /**
     * @return get map from class
     */
    public Map<String, Crit> getCriteriaMap() {
        return criteriaMap;
    }
}
