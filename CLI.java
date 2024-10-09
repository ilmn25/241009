package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private static Scanner _scanner = new Scanner(System.in);
    private static String[] _command;


    //TODO THIS IS FOR DEBUGGING, DELETE FROM BUILD
    private static List<String> defaultInputs = new ArrayList<>();
    static {
        defaultInputs.add("help");
        defaultInputs.add("newDisk disk 110101010");
        defaultInputs.add("newDoc a txt xerctvybunimxrctvybun");
        defaultInputs.add("newDoc b txt xerctvybunimawdawdxrctvybun");
        defaultInputs.add("newDoc c java xerctvybunimaaaaaxrctvybun");
        defaultInputs.add("newDir folder");
        defaultInputs.add("changeDir folder");
        defaultInputs.add("newDoc aa txt xerctvybunimawdawdxrctvybun");
        defaultInputs.add("newDoc bb css xerctvybunimawdawdxrctvybun");
        defaultInputs.add("newDoc cc java xerctvybunimawdawdxrctvybun");
        defaultInputs.add("return");
        defaultInputs.add("rList");
        defaultInputs.add("list");
        defaultInputs.add("newNegation bb IsDocument");
        defaultInputs.add("search bb");




//        defaultInputs.add("newSimpleCri aa bb cc dd");
//        defaultInputs.add("newNegation bb aa");
//        defaultInputs.add("newNegation aa aa");
//        defaultInputs.add("newBinaryCri cc bb && aa");
//        defaultInputs.add("printAllCriteria");
//        defaultInputs.add("list");
//        defaultInputs.add("newDisk disk 110101010");
//        defaultInputs.add("newDoc aa txt xerctvybunimawdawdxrctvybun");
//        defaultInputs.add("newDoc bb css xerctvybunimawdawdxrctvybun");
//        defaultInputs.add("newDoc cc java xerctvybunimawdawdxrctvybun");
//        defaultInputs.add("delete bb");
//        defaultInputs.add("rename cc dd");
//        defaultInputs.add("list");
//        defaultInputs.add("newDoc ee html xerctvybunimawdawdxrctvybun");
//        defaultInputs.add("list");
    }

    private static void input() {
        if (!defaultInputs.isEmpty()) {
            String inputCommand = defaultInputs.remove(0);
            output("♿ Autosend > " + inputCommand);
            _command = inputCommand.split(" ");
        } else {
            _command = _scanner.nextLine().split(" ");
        }
        output("--------------");
    }



    public static void output(String message) {
        System.out.println(message);
    }

    // ====== main loop ======

    public static void Run() {
        output("======================\n= Welcome to the Comp Virtual File System (CVFS) =");
        while (true) {
            output("===================================================================");
            //todo show working directory
            //todo symbol ‘$’ to denote the working directory and the symbol ‘:’ to separate the file names in a path.
            //todo For example, path “$:xyz” refers to a file named “xyz” inside the working directory. Depending
            //todo on whether the file actually exists in the working directory or not, the path may be valid or invalid.
            input();

            switch (_command[0]) {
                case "help":
                    printCommands();
                    break;
                case "return":
                    CVFS.dirMove(null);
                    break;

                case "changeDir":
                    changeDir();
                    break;
                case "save":
                    save();
                    break;
                case "load":
                    load();
                    break;
                case "quit":
                    _scanner.close();
                    return;

                case "newDisk":
                    newDisk();
                    break;
                case "newDoc":
                    newDoc();
                    break;
                case "newDir":
                    newDir();
                    break;
                case "delete":
                    delete();
                    break;
                case "rename":
                    rename();
                    break;

                case "list":
                    list();
                    break;
                case "rList":
                    rList();
                    break;
                case "search":
                    search();
                    break;
                case "rSearch":
                    rSearch();
                    break;

                case "newSimpleCri":
                    newSimpleCri();
                    break;
                case "newNegation":
                    newNegation();
                    break;
                case "newBinaryCri":
                    newBinaryCri();
                    break;
                case "printAllCriteria":
                    printAllCriteria();
                    break;

                default:
                    output("Unknown _command.");
            }
        }
    }


    private static void save() {
        //todo check if command strings are correct
        CVFS.diskSave(_command[1]);
    }

    private static void load() {
        //todo check if command strings are correct
        CVFS.diskLoad(_command[1]);
    }

    private static void changeDir() {
        //todo check if command strings are correct
        CVFS.dirMove(_command[1]);
    }

    private static void newDisk() {
        //todo check if command strings are correct
        try {
            CVFS.diskNew(_command[1], Integer.parseInt(_command[2]));
        } catch (NumberFormatException e) {
            output("Error: Size must be an integer.");
        }
    }

    private static void newDoc() {
        //todo check if command strings are correct
        CVFS.fileNew(_command[1], _command[2], _command[3]);
    }

    private static void newDir() {
        //todo check if command strings are correct
        CVFS.fileNew(_command[1]);
    }

    private static void delete() {
        //todo check if command strings are correct
        CVFS.fileDelete(_command[1]);
    }

    private static void rename() {
        //todo check if command strings are correct
        CVFS.fileRename(_command[1], _command[2]);
    }

    private static void list() {
        CVFS.list(false, null);
    }

    private static void rList() {
        CVFS.list(true, null);
    }

    private static void search() {
        //todo check if command strings are correct
        CVFS.list(false, Criterion.getCriterion(_command[1]));
    }

    private static void rSearch() {
        //todo check if command strings are correct
        CVFS.list(true, Criterion.getCriterion(_command[1]));
    }

    private static void newSimpleCri() {
        //todo check if command strings are correct
        new Criterion(_command[1], _command[2], _command[3], _command[4]);
    }

    private static void newNegation() {
        //todo check if command strings are correct
        Criterion tempA = Criterion.getCriterion(_command[1]);
        if (tempA != null) {
            output(_command[1] + " already exists");
            return;
        }

        tempA = Criterion.getCriterion(_command[2]);
        if (tempA != null) {
            new Criterion(_command[1], tempA);
        } else {
            CLI.output("criteria do not exist");
        }
    }

    private static void newBinaryCri() {
        //todo check if command strings are correct
        Criterion tempA = Criterion.getCriterion(_command[1]);
        if (tempA != null) {
            output(_command[1] + " already exists");
            return;
        }

        tempA = Criterion.getCriterion(_command[2]);
        Criterion tempB = Criterion.getCriterion(_command[4]);

        if (tempA != null && tempB != null) {
            new Criterion(_command[1], tempA, tempB, _command[3]);
        } else {
            CLI.output("One or both criteria do not exist");
        }
    }

    private static void printAllCriteria() {
        Criterion.list();
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