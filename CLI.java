package hk.edu.polyu.comp.comp2021.cvfs.model;

import hk.edu.polyu.comp.comp2021.cvfs.model.crit.Crit;
import hk.edu.polyu.comp.comp2021.cvfs.model.crit.CritB;
import hk.edu.polyu.comp.comp2021.cvfs.model.crit.CritN;
import hk.edu.polyu.comp.comp2021.cvfs.model.crit.CritS;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * command line interface class, handles input output and input syntax check etc
 */
public class CLI {
    private static final Scanner _scanner = new Scanner(System.in);
    private static String[] _command;


    //TODO THIS IS FOR DEBUGGING, DELETE FROM BUILD
    private static List<String> defaultInputs = new ArrayList<>();
    static {
        defaultInputs.add("newDisk 13131");
        defaultInputs.add("newDoc a txt exampleContent");
        defaultInputs.add("newDoc b txt anotherExampleContent");
        defaultInputs.add("newDoc c java javaExampleContent");
        defaultInputs.add("newDir exampleFolder");
        defaultInputs.add("changeDir exampleFolder");
        defaultInputs.add("newDir subFolder");
        defaultInputs.add("changeDir exampleFolder");
        defaultInputs.add("changeDir subFolder");
        defaultInputs.add("newDoc ddd txt subFolderContent");
        defaultInputs.add("newDoc ed css subFolderCSSContent");
        defaultInputs.add("newDoc f java subFolderJavaContent");
        defaultInputs.add("rList");
        defaultInputs.add("list");
        defaultInputs.add("newSimpleCri aaa name contains \"d\"");
        defaultInputs.add("newSimpleCri aa name contains \"d\"");
        defaultInputs.add("newSimpleCri bb type equals \"txt\"");
        defaultInputs.add("newSimpleCri cc size > 100");
        defaultInputs.add("newNegation dd IsDocument");
        defaultInputs.add("newBinaryCri ee aa && bb");
        defaultInputs.add("newBinaryCri ff cc || dd");
        defaultInputs.add("printAllCriteria");
        defaultInputs.add("search aa");
        defaultInputs.add("rSearch bb");
        defaultInputs.add("save C:/Users/illuminae/Downloads/");
        defaultInputs.add("newDisk 13131");
        defaultInputs.add("load C:/Users/illuminae/Downloads/");
        defaultInputs.add("rList");
        defaultInputs.add("changeDir exampleFolder");
        defaultInputs.add("changeDir subFolder");
        defaultInputs.add("rList");
        defaultInputs.add("list");
        defaultInputs.add("printAllCriteria");
        defaultInputs.add("search aa");
        defaultInputs.add("rSearch bb");
        defaultInputs.add("newDoc ddd txt subFolderContent");
        defaultInputs.add("newDoc ddd css subFolderCSSContent");
        defaultInputs.add("newDoc ddd java subFolderJavaContent");
        defaultInputs.add("newDir ddd");
        defaultInputs.add("newDoc ddd txt subFolderContent");
        defaultInputs.add("newDoc ddd css subFolderCSSContent");
        defaultInputs.add("newDoc ddd java subFolderJavaContent");
        defaultInputs.add("newDir ddd");
        defaultInputs.add("list");
        defaultInputs.add("rList");


    }

    private static void input() {
        if (!defaultInputs.isEmpty()) {
            String inputCommand = defaultInputs.remove(0);
            System.out.print("> ♿ " + inputCommand + "\n");
            _command = inputCommand.split(" ");
        } else {
            System.out.print("> ");
            _command = _scanner.nextLine().split(" ");
        }
    }

    /**
     * @param message string to print in interface console
     */
    public static void output(String message) {
        System.out.println(message);
    }

    /**
     * main loop of input and output
     */
    public static void Run() {
        output("======================\n= Welcome to the Comp Virtual File System (CVFS) =");
        while (true) {
            output("===================================================================");
            input();

            switch (_command[0]) {
                case "changeDir":
                    if (!CVFS.hasDisk()) continue;
                    if (!checkArgCount(2)) continue;
                    changeDir();
                    break;
                case "save":
                    if (!CVFS.hasDisk()) continue;
                    if (!checkArgCount(2)) continue;
                    save();
                    break;
                case "load":
                    if (!checkArgCount(2)) continue;
                    load();
                    break;
                case "quit":
                    if (!checkArgCount(1)) continue;
                    _scanner.close();
                    return;
                case "newDisk":
                    if (!checkArgCount(2)) continue;
                    newDisk();
                    break;
                case "newDoc":
                    if (!CVFS.hasDisk()) continue;
                    if (!checkArgCount(4)) continue;
                    newDoc();
                    break;
                case "newDir":
                    if (!CVFS.hasDisk()) continue;
                    if (!checkArgCount(2)) continue;
                    newDir();
                    break;
                case "delete":
                    if (!CVFS.hasDisk()) continue;
                    if (!checkArgCount(2)) continue;
                    delete();
                    break;
                case "rename":
                    if (!CVFS.hasDisk()) continue;
                    if (!checkArgCount(3)) continue;
                    rename();
                    break;
                case "list":
                    if (!CVFS.hasDisk()) continue;
                    if (!checkArgCount(1)) continue;
                    list();
                    break;
                case "rList":
                    if (!CVFS.hasDisk()) continue;
                    if (!checkArgCount(1)) continue;
                    rList();
                    break;
                case "search":
                    if (!CVFS.hasDisk()) continue;
                    if (!checkArgCount(2)) continue;
                    search();
                    break;
                case "rSearch":
                    if (!CVFS.hasDisk()) continue;
                    if (!checkArgCount(2)) continue;
                    rSearch();
                    break;
                case "newSimpleCri":
                    if (!checkArgCount(5)) continue;
                    newSimpleCri();
                    break;
                case "newNegation":
                    if (!checkArgCount(3)) continue;
                    newNegation();
                    break;
                case "newBinaryCri":
                    if (!checkArgCount(5)) continue;
                    newBinaryCri();
                    break;
                case "printAllCriteria":
                    if (!checkArgCount(1)) continue;
                    printAllCriteria();
                    break;
                default:
                    output("input error: command not found, please check capitalization and spelling");
                    break;
            }
        }
    }

    /**
     * @param value string that needs branket removed, like value parameter of criterion
     * @return value with branket removed
     */
    public static String removeQuotes(String value) {
        if (value != null && value.length() > 1 && value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }

    private static boolean checkArgCount(int count) {
        if (_command.length == count) {
            return true;
        }
        output("input error: wrong argument count for " + _command[0] +
                ", expected " + (count-1) + ", received " + (_command.length-1));
        return false;
    }

    private static void save() {
        String path = _command[1];
        if (Files.exists(Paths.get(path))) {
            Save.diskSave(path + "disk.dat");
        } else {
            CLI.output("path error: the specified path does not exist");
        }
    }

    private static void load() {
        String path = _command[1];
        if (Files.exists(Paths.get(path + "disk.dat"))) {
            Save.diskLoad(path + "disk.dat");
        } else {
            CLI.output("path error: the specified path does not exist, or disk.dat is not present at the path");
        }
    }

    private static void changeDir() {
        //todo check if command strings are correct
        CVFS.dirMove(_command[1]);
    }

    private static void newDisk() {
        try {
            int size = Integer.parseInt(_command[1]);
            if (size > 0) {
                CVFS.diskNew(size);
            } else output("input error: size cannot be 0 or under");
        } catch (NumberFormatException e) {
            output("input error: size must be an integer and has a positive value under " + Integer.MAX_VALUE);
        }
    }

    private static void newDoc() {
        if (!_command[2].equals("txt")
                && !_command[2].equals("java")
                && !_command[2].equals("css")
                && !_command[2].equals("html")) {

            CLI.output("Invalid file type " + _command[2]
                    + "must be \"txt\", \"java\", \"css\", or \"html");
            return;
        }
        CVFS.docNew(_command[1], _command[2], _command[3]);
    }


    private static void newDir() {
        CVFS.dirNew(_command[1]);
    }

    private static void delete() {
        CVFS.fileDelete(_command[1]);
    }

    private static void rename() {
        CVFS.fileRename(_command[1], _command[2]);
    }

    private static void list() {
        CVFS.getDir().list(false, null);
    }

    private static void rList() {
        CVFS.getDir().list(true, null);
    }

    private static void search() {
        Crit tempCriterion = Crit.getCriterion(_command[1]);
        if (tempCriterion == null) {
            CLI.output("input error: no criterion found with the name " + _command[1]);
            return;
        }
        CVFS.getDir().list(false, tempCriterion);
    }

    private static void rSearch() {
        Crit tempCriterion = Crit.getCriterion(_command[1]);
        if (tempCriterion == null) {
            CLI.output("input error: no criterion found with the name " + _command[1]);
            return;
        }
        CVFS.getDir().list(true, tempCriterion);
    }

    private static void newSimpleCri() {
        if (Crit.getCriterion(_command[1]) != null) {
            output("command error: " + _command[1] + " already exists");
            return;
        }

        if (!_command[1].matches("^[a-zA-Z]{2}$")) {
            CLI.output("input error: new criterion name must contain exactly two English letters.");
            return;
        }

        switch (_command[2]) {
            case "name":
                if (!_command[3].equals("contains") || !_command[4].matches("^\".*\"$")) {
                    CLI.output("input error: for 'name', operator must be 'contains' and value must be surrounded by double quotes (\")");
                    return;
                }
                _command[4] = removeQuotes(_command[4]);
                break;
            case "type":
                if (!_command[3].equals("equals") || !_command[4].matches("^\".*\"$")) {
                    CLI.output("input error: for 'type', operator must be 'equals' and value must be \"txt\", \"java\", \"css\",or \"html\" with double quotes included (\")");
                    return;
                }
                _command[4] = removeQuotes(_command[4]);
                break;
            case "size":
                if (!_command[3].matches(">|<|>=|<=|==|!=") || !_command[4].matches("^\\d+$")) {
                    CLI.output("input error: for 'size', operator must be one of '>', '<', '>=', '<=', '==', '!=' and value must be an integer.");
                    return;
                }
                break;
            default:
                CLI.output("input error: attribute must be 'name', 'type', or 'size'");
                return;
        }

        new CritS(_command[1], _command[2], _command[3], _command[4]);
    }


    private static void newNegation() {
        if (Crit.getCriterion(_command[1]) != null) {
            output("command error: " + _command[1] + " already exists");
            return;
        }

        if (!_command[1].matches("^[a-zA-Z]{2}$")) {
            CLI.output("input error: new criterion name must contain exactly two English letters.");
            return;
        }

        Crit tempA = Crit.getCriterion(_command[2]);
        if (tempA != null) {
            new CritN(_command[1], tempA);
        } else {
            CLI.output("command error: " + _command[2] + " do not exist");
        }
    }

    private static void newBinaryCri() {
        if (Crit.getCriterion(_command[1]) != null) {
            output("command error: " + _command[1] + " already exists");
            return;
        }

        if (!_command[3].equals("&&") && !_command[3].equals("||")) {
            CLI.output("input error: operator should be '&&' or '||'");
            return;
        }

        Crit tempA = Crit.getCriterion(_command[2]);
        Crit tempB = Crit.getCriterion(_command[4]);
        if (tempA != null && tempB != null) {
            new CritB(_command[1], tempA, tempB, _command[3]);
        } else {
            CLI.output("command error: one or both criteria do not exist");
        }
    }

    private static void printAllCriteria() {
        Crit.list();
    }


















    private static void printCommands() {
        output("newDisk diskSize");
        output("newDoc docName docType docContent");
        output("newDir dirName");
        output("delete fileName");
        output("rename oldFileName newFileName");
        output("changeDir dirName");
        output("list");
        output("rList");
        output("newSimpleCri criName attrName op val");
        output("newNegation criName1 criName2");
        output("newBinaryCri criName1 criName3 logicOp criName4");
        output("printAllCriteria");
        output("search criName");
        output("rSearch criName");
        output("save path");
        output("load path");
        output("quit");
    }
}