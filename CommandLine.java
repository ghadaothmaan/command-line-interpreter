/*
 * operating systems command line interpreter assignment 1
 */

//import java.nio.file.Files;
//import java.nio.file.Paths;
import java.util.*;
import java.text.*;
import java.io.*;
//import java.lang.reflect.Array;
//import java.io.PrintWriter;

public class CommandLine {

    public static String workingDirectory = "/Users/gee";
    //public static int slashie = workingDirectory.lastIndexOf("/");
    //public static String wd = workingDirectory.substring(workingDirectory.lastIndexOf("/") + 1);

    // cd, ls, clear, mkdir, rmdir, mv, cp, rm, pwd, cat, date, args, more, exit
    public static String[] helps = {"cd: Changes directory to default\ncd ~: Changes directory to default\n" +
            "cd [arg]: Changes directory to arg\ncd .. : Goes to parent directory\n", "ls: Lists contents of working directory\n",
            "clear: Clears terminal screen\n", "mkdir [arg]: Makes a new directory 'arg' in the working directory\n",
            "rmdir [arg]: Removes 'arg' directory if valid\n", "mv [arg1] [arg2]: Moves arg1 into arg2\n",
            "cp [arg1] [arg2]: Copies arg1 into arg2\n", "rm [arg1] [arg2] [arg..]: Removes arg\n" , "pwd: Prints working directory\n",
            "cat [arg1] [arg2] [arg..]: Concatenates content of files and prints to standard output\n", "date: Prints current date and time\n",
            "args: Lists all command arguments\n", "more [arg1]: Prints content page by page\n" ,"help: prints help of all commands\n", "exit: Stops all\n"
    };

    public static String[] args = {"cd : default directory\ncd [~]: default directory\ncd [..]: parent directory\ncd [path]: goes to path\n",
            "ls: (takes no arguments)\n", "clear: (takes no arguments)\n", "mkdir [arg]: arg = new directory to be made\n",
            "rmdir [arg]: arg = directory to be deleted\n", "mv [arg1] [arg2]: arg1 = directory or file to be moved\narg2 = destination\n",
            "cp [arg1] [arg2]: arg1 = directory or file to be copied\narg2 = destination\n", "rm [arg1] [arg2] [arg..]: arg1, arg2, arg.. = files to be removed\n",
            "pwd: (takes no arguments)\n", "cat [arg1] [arg2] [arg..]: arg1, arg2, arg.. = file to be concatenated to the rest\n",
            "date: (takes no arguments)\n", "args: (takes no arguments)\n", "more [arg1] arg1 = file to be printed\n",
            "exit: (takes no arguments)\n", "? [arg1]: command for help\n"
    };

    public static void main(String[] args) {
        Boolean exit = false;
        //System.out.println(wd);
//        if (workingDirectory == "/Users/gee") // its not working mwahmwah thumbs down
//            System.out.print("gees-MacBook-Air:~ gee$ ");
//        else
//            workingDirectory = workingDirectory.substring(workingDirectory.lastIndexOf("/") + 1);

        while (!exit) {
            System.out.print("gees-MacBook-Air:" + workingDirectory.substring(workingDirectory.lastIndexOf("/") + 1) + " gee$ ");
            Scanner cin = new Scanner(System.in);
            String in = cin.nextLine();
            String arr[] = in.split(";");
            for (String input : arr) {
                Boolean redirect1 = false, redirect2 = false;
                String command, argument;
                if (input.contains(">>")) {
                    redirect2 = true;
                    command = input.substring(0, input.indexOf(">"));
                    argument = input.substring(input.lastIndexOf(">") + 1, input.length());
                }
                else if (input.contains(">")) {
                    redirect1 = true;
                    command = input.substring(0, input.indexOf(">"));
                    argument = input.substring(input.lastIndexOf(">") + 1, input.length());
                }
                // pwd > blah.txt; pwd >> hi.txt pwd>blah.txt
                else if (input.contains(" ")) {
                    command = input.substring(0, input.indexOf(" "));
                    argument = input.substring(input.indexOf(" ") + 1, input.length());
                } else {
                    command = input;
                    argument = "";
                }
                // System.out.println(command);
                //System.out.println(argument);

                if (command.equals("exit")) { // a switch is a good idea too :)
                    exit = true;
                    break;
                } else if (command.equals("clear"))
                    clear();
                else if (command.equals("date")) {
                    if (redirect1) {
                        overrideFile(date(), argument);
                    }
                    else if (redirect2) {
                        appendToFile(date(), argument);
                    }
                    else {
                        System.out.println(date());
                    }
                }
                else if (command.equals("pwd")) {
                    if (redirect1) {
                        overrideFile(pwd(), argument);
                    } else if (redirect2) {
                        appendToFile(pwd(), argument);
                    } else {
                        System.out.println(pwd());
                    }
                }
                else if (command.equals("ls")) {
                    if (redirect1) {
                        overrideFile(ls(), argument);
                    } else if (redirect2) {
                        appendToFile(ls(), argument);
                    }
                    else {
                        ArrayList<String> res = ls();
                        for (String s : res)
                            System.out.println(s);
                    }
                }
                else if (command.equals("cd")) {
                    if (redirect1) {
                        overrideFile(cd(argument), argument);
                    }
                    else if (redirect2) {
                        appendToFile(cd(argument), argument);
                    }
                    else {
                        System.out.print(cd(argument));
                    }
                }
                else if (command.equals("mkdir")) {
                    if (redirect1) {
                        overrideFile(mkdir(argument), argument);
                    }
                    else if (redirect2) {
                        appendToFile(mkdir(argument), argument);
                    }
                    else System.out.print(mkdir(argument));
                }
                else if (command.equals("rmdir")) {
                    if (redirect1) {
                        overrideFile(rmdir(argument), argument);
                    }
                    else if (redirect2) {
                        appendToFile(rmdir(argument), argument);
                    }
                    else System.out.print(rmdir(argument));
                }
                else if (command.equals("rm")) {
                    if (redirect1) {
                        overrideFile(rm(argument), argument);
                    }
                    else if (redirect2) {
                        appendToFile(rm(argument), argument);
                    }
                    else System.out.print(rm(argument));
                }
                else if (command.equals("mv")) {
                    if (redirect1) {
                        overrideFile(mv(argument), argument);
                    }
                    else if (redirect2) {
                        appendToFile(mv(argument), argument);
                    }
                    else System.out.print(mv(argument));
                }
                else if (command.equals("more")) {
                    //more(); // ignore
                    if (redirect1) {
                        overrideFile(cat(argument), argument);
                    }
                    else if(redirect2) {
                        appendToFile(cat(argument), argument);
                    }
                    else {
                        ArrayList<String> res = cat(argument);
                        int lines = 20;
                        for (String s : res) {
                            lines--;
                            if (lines > 0)
                                System.out.println(s);
                            else if (lines <= 0) {
                                System.out.print("--More--");
                                try {
                                    int read = System.in.read(new byte[2]);
                                    System.out.println(s);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                else if (command.equals("help")) {
                    if (redirect1) {
                        overrideFile(help(), argument);
                    } else if (redirect2) {
                        appendToFile(help(), argument);
                    } else {
                        ArrayList<String> res = help();
                        for (String s : res)
                            System.out.print(s);
                    }
                }
                else if (command.equals("cat")) {
                    if (redirect1) {
                        overrideFile(cat(argument), argument);
                    } else if (redirect2) {
                        appendToFile(cat(argument), argument);
                    } else {
                        ArrayList<String> res = cat(argument);
                        for (String s : res)
                            System.out.println(s);
                    }
                }
                else if (command.equals("cp")) {
                    if (redirect1) {
                        overrideFile(cp(argument), argument);
                    }
                    else if (redirect2) {
                        appendToFile(cp(argument), argument);
                    }
                    else System.out.print(cp(argument));
                }
                else if(command.equals("?")) {
                    if (redirect1) {
                        overrideFile(helpme(argument), argument);
                    }
                    else if (redirect2) {
                        appendToFile(helpme(argument), argument);
                    }
                    else System.out.print(helpme(argument));
                }
                else if (command.equals("args")) {
                    if (redirect1) {
                        overrideFile(args(), argument);
                    } else if (redirect2) {
                        appendToFile(args(), argument);
                    } else {
                        ArrayList<String> res = args();
                        for (String s : res)
                            System.out.print(s);
                    }
                }
                else
                    System.out.println(error(command));
            }
        }
    }

    public static String error(String thatswrong) {
        return "-bash: " + thatswrong + ": command not found";
    }

    public static void clear() {
        for (int i = 0; i < 100; i++)
            System.out.println();
    }

    public static String date() {
        Format date = new SimpleDateFormat("E MMM d HH:mm:ss zzz yyyy ");
        return date.format(new Date());
    }

    public static String pwd() {
        return workingDirectory;
    }

    public static ArrayList<String> ls() {
        ArrayList list = new ArrayList();
        File[] files = new File(workingDirectory).listFiles();
        for (File file : files) {
            if (file.getName().charAt(0) != '.') // FIX MME!!!! :)
                list.add(file.getName());
        }
        return list;
    }

    public static String cd(String arg) {
        String res = "";
        if (arg.equals("~")) {
            workingDirectory = "/Users/gee";
        }
/*
        else if (arg.contains("..")) { // try to fix me
            // cd "Operating Systems"/../Multimedia
        }
*/
        else if (arg.length() == 0) {
            workingDirectory = "/Users/gee";
        } else if (arg.equals("..")) {
            if (workingDirectory.lastIndexOf("/") == workingDirectory.length() - 1) {
                workingDirectory = workingDirectory.substring(0, workingDirectory.length() - 1);
            }
            workingDirectory = workingDirectory.substring(0, workingDirectory.lastIndexOf("/"));
        } else {
            if (arg.contains("/Users")) { // if user entered a complete directory
                if (new File(arg).isDirectory()) { // if its valid
                    workingDirectory = arg; // change wd
//                     if (workingDirectory.charAt(workingDirectory.length() - 1) != '/') {
//                       workingDirectory += '/';
//                    }
                } else { // not a valid directory
                    res = "-bash: cd: " + arg + ": No such file or directory\n";
                }
            } else { // part of the path
                String newPath;
                if (workingDirectory.charAt(workingDirectory.length() - 1) != '/') {
                    newPath = workingDirectory + '/' + arg;
                } else newPath = workingDirectory + arg;
                if (new File(newPath).isDirectory()) {
                    workingDirectory = newPath;
                } else {
                    res = "-bash: cd: " + arg + ": No such file or directory\n";
                }
            }
        }
        return res;
    }

    public static String mkdir(String arg) {
        String res = "";
        if (arg.length() == 0)
            res = "-bash: mkdir: missing operand\nTry '? mkdir' for more information.\n";
        else {
            if (workingDirectory.charAt(workingDirectory.length() - 1) != '/') {
                workingDirectory += "/";
            }
            String directory = workingDirectory + arg + "/";
            File newDir = new File(directory);
            if (newDir.exists()) {
                res = "-bash: mkdir: cannot create directory ‘" + arg + "’: File exists\n";
            }
            newDir.mkdir();
//             System.out.println(directory);
//            if (!newDir.exists()) {
//                System.out.println("didnt make it");
//                workingDirectory += '/'; // fixes the slash bug
//                System.out.println(workingDirectory);
//                newDir = new File(workingDirectory + arg);
//                System.out.println(workingDirectory + arg);
//                newDir.mkdir();
//                if (newDir.exists()) {
//                    System.out.println("cant");
//                }
//            }
//            try {
//                Files.createDirectory(Paths.get(workingDirectory + arg));
//            }
//            catch(IOException e) {
//                e.printStackTrace();
//            }
        }
        return res;
    }

    public static String rmdir(String arg) {
        String res = "";
        if (workingDirectory.charAt(workingDirectory.length() - 1) != '/') {
            workingDirectory += "/"; // put an ending slash
        }
        String toRemove = "";
        if (arg.contains("/Users")) { // if user entered a complete directory
            if (new File(arg).isDirectory()) { // if its valid
                toRemove = arg;
            } else {
                res = "-bash: rmdir: failed to remove ‘" + arg + "’: No such file or directory\n";
            }
        } else {
            toRemove = workingDirectory + arg;
        }

        File dirToRemove = new File(toRemove);
//        System.out.println(dirToRemove);
//        System.out.println(dirToRemove.isDirectory());

        if (dirToRemove.isDirectory()) {
            if (dirToRemove.list().length == 0)
                dirToRemove.delete();
            else
                res = "-bash: rmdir: failed to remove ‘" + arg + "’: Directory not empty\n";
        } else
            res = "-bash: rmdir: failed to remove ‘" + arg + "’: No such file or directory\n";
        return res;
    }


    public static String rm(String arg) {
        String res = "";
        if (workingDirectory.charAt(workingDirectory.length() - 1) != '/') {
            workingDirectory += "/"; // put an ending slash
        }
        String[] args;
        args = arg.split(" ");
        for (String argument : args) {
            String toRemove = "";
            if (argument.contains("/Users")) { // if user entered a complete directory
                if (new File(argument).isFile()) { // if its valid
                    toRemove = argument;
                } else {
                    res = "-bash: rm: failed to remove ‘" + argument + "’: No such file or directory\n";
                }
            } else {
                toRemove = workingDirectory + argument;
            }
            File fileToRemove = new File(toRemove);
            if (fileToRemove.isFile()) {
                fileToRemove.delete();
            } else
                res = "-bash: rm: failed to remove ‘" + argument + "’: No such file or directory\n";
        }
        return res;
    }

    public static String mv(String arg) {
        String res = "";
        if (workingDirectory.charAt(workingDirectory.length() - 1) != '/') {
            workingDirectory += "/"; // put an ending slash
        }
        String[] args;
        args = arg.split(" ");
        String moveThis = args[0];
        if (args.length == 1) {
            res = "-bash: mv: missing destination file operand after ‘" + arg + "’\n" + "Try '? mv' for more information.\n";
            return res;
        } else {
            String toThis = args[1];
            if (!moveThis.contains("/Users")) {
                moveThis = workingDirectory + moveThis;
                if (moveThis.charAt(moveThis.length() - 1) == '/') // remove last slash
                    moveThis = moveThis.substring(0, moveThis.length() - 1);
            }
            if (!toThis.contains("/Users")) {
                toThis = workingDirectory + toThis;
            }
            String toMove = moveThis.substring(moveThis.lastIndexOf("/"), moveThis.length());
            // home/menna/music/RocknRoll/rock take only <- moveThis
            // home/menna/Documents <- toThis
            // /rock.txt <- toMove
            File file = new File(moveThis);
            //System.out.println(file.isFile());
            File destination = new File(toThis);
            if (!destination.isDirectory()) {
                res = "Not a valid directory\n"; //UGHHH FIX MEEEEEEE
                return res;
            }

            if (file.isDirectory()) { // if its a dir // check if first arg is dir
                //file.mkdir(); // and then rmdir(moveThis)
                File f = new File(toThis + toMove);
                f.mkdir();
                rmdir(moveThis);
            } else if (file.isFile()) { // else check if its a file
                try { // toMove -> toThis;
                    File f = new File(toThis + toMove);
                    f.createNewFile();
                    rm(moveThis);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                res = "-bash: mv: cannot stat ‘" + args[0] + "’: No such file or directory\n";
            }
        }
        return res;
    }

    public static String cp(String arg) {
        String res = "";
        if (workingDirectory.charAt(workingDirectory.length() - 1) != '/') {
            workingDirectory += "/"; // put an ending slash
        }
        String[] args;
        args = arg.split(" ");
        String copyThis = args[0];
        if (args.length == 1) {
            return "-bash: cp: missing destination file operand after ‘" + arg + "’\n" + "Try '? cp' for more information.\n";
        } else {
            String toThis = args[1];
            if (!copyThis.contains("/Users")) {
                copyThis = workingDirectory + copyThis;
                if (copyThis.charAt(copyThis.length() - 1) == '/') // remove last slash
                    copyThis = copyThis.substring(0, copyThis.length() - 1);
            }
            if (!toThis.contains("/Users")) {
                toThis = workingDirectory + toThis;
            }
            String toCopy = copyThis.substring(copyThis.lastIndexOf("/"), copyThis.length());

            File file = new File(copyThis);
            //System.out.println(file.isFile());
            File destination = new File(toThis);
            if (!destination.isDirectory()) {
                return "Not a valid directory\n"; //UGHHH FIX MEEEEEE
            }

            if (file.isDirectory()) { // if its a dir // check if first arg is dir
                // file.mkdir(); // and then rmdir(moveThis)
                //file.mkdir();
                File f = new File(toThis + toCopy);
                f.mkdir();
            } else if (file.isFile()) { // else check if its a file
                //else if its a file
                // toMove -> toThis;
                try {
                    File f = new File(toThis + toCopy);
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                res = "-bash: cp: cannot stat ‘" + args[0] + "’: No such file or directory\n";
            }
        }
        return res;
    }

    public static ArrayList<String> cat(String arg) {
        ArrayList res = new ArrayList();
        if (workingDirectory.charAt(workingDirectory.length() - 1) != '/') {
            workingDirectory += "/"; // put an ending slash
        } // home/menna/Documents/  hi.txt
        String[] args;
        args = arg.split(" ");
        String path = "";
        for (String argument : args) {
            if (!argument.contains("/Users")) {
                path = workingDirectory + argument;
            }
            else path = argument;
            File dir = new File(path);
            if (!dir.isFile()) {
                res.add("-bash: cat: " + argument + ": No such file or directory\n");
            }
            else {
                try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        res.add(line);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

//    public static void more() {
//    }

    public static ArrayList<String> help() {
        ArrayList res = new ArrayList();
        for (String s : helps)
            res.add(s);
        return res;
    }

    public static ArrayList<String> args() {
        ArrayList res = new ArrayList();
        for (String s : args)
            res.add(s);
        return res;
    }

    public static String helpme(String arg) {
        String res;
        // cd, ls, clear, mkdir, rmdir, mv, cp, rm, pwd, cat, date, args, more, exit
        if (arg.equals("cd")) res = helps[0];
        else if (arg.equals("ls")) res = helps[1];
        else if (arg.equals("clear")) res = helps[2];
        else if (arg.equals("mkdir")) res = helps[3];
        else if (arg.equals("rmdir")) res = helps[4];
        else if (arg.equals("mv")) res = helps[5];
        else if (arg.equals("cp")) res = helps[6];
        else if (arg.equals("rm")) res = helps[7];
        else if (arg.equals("pwd")) res = helps[8];
        else if (arg.equals("cat")) res = helps[9];
        else if (arg.equals("date")) res = helps[10];
        else if (arg.equals("args")) res = helps[11];
        else if (arg.equals("more")) res = helps[12];
        else if (arg.equals("help")) res = helps[13];
        else if (arg.equals("exit")) res = helps[14];
        else res = "-bash: " + arg + ": command not found\n";
        return res;
    }

//        public static void redirect1(String command, String arg) {
//        File file = new File(arg);
//        //PrintStream printStreamToFile = new PrintStream(file);
//        //System.setOut(printStreamToFile);
//    }

    public static void overrideFile(String write, String arg) {
        String dir = "";
        if (arg.contains("/Users")) { // if user entered a complete directory
            if (new File(arg).isFile()) { // if its valid
                dir = arg;
            } else { // not a valid directory
                System.out.println("-bash: cd: " + arg + ": No such file or directory\n");
            }
        } else { // part of the path
            String newPath;
            if (workingDirectory.charAt(workingDirectory.length() - 1) != '/') {
                newPath = workingDirectory + '/' + arg;
            } else newPath = workingDirectory + arg;
            if (new File(newPath).isFile()) {
                try(PrintStream ps = new PrintStream(newPath)) {
                    ps.println(write);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("-bash: cd: " + arg + ": No such file or directory\n");
            }
        }
    }

    public static void overrideFile(ArrayList<String> write, String arg) { // >
        String dir = "";
        if (arg.contains("/Users")) { // if user entered a complete directory
            if (new File(arg).isFile()) { // if its valid
                dir = arg;
            } else { // not a valid directory
                System.out.println("-bash: cd: " + arg + ": No such file or directory\n");
            }
        } else { // part of the path
            String newPath;
            if (workingDirectory.charAt(workingDirectory.length() - 1) != '/') {
                newPath = workingDirectory + '/' + arg;
            } else newPath = workingDirectory + arg;
            if (new File(newPath).isFile()) {
                try(PrintStream ps = new PrintStream(newPath)) {
                    for (String s: write)
                        ps.println(s);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("-bash: cd: " + arg + ": No such file or directory\n");
            }
        }
    }

    public static void appendToFile(String write, String arg) {
        String dir = "";
        if (arg.contains("/Users")) { // if user entered a complete directory
            if (new File(arg).isFile()) { // if its valid
                dir = arg;
            } else { // not a valid directory
                System.out.println("-bash: cd: " + arg + ": No such file or directory\n");
            }
        } else { // part of the path
            String newPath;
            if (workingDirectory.charAt(workingDirectory.length() - 1) != '/') {
                newPath = workingDirectory + '/' + arg;
            } else newPath = workingDirectory + arg;
            if (new File(newPath).isFile()) {
                try(FileWriter fw = new FileWriter(newPath, true)) { // true will append new string
                    fw.write(write); //appends the string
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("-bash: cd: " + arg + ": No such file or directory\n");
            }
        }
    }

    public static void appendToFile(ArrayList<String> write, String arg) { // >>
        String dir = "";
        if (arg.contains("/Users")) { // if user entered a complete directory
            if (new File(arg).isFile()) { // if its valid
                dir = arg;
            } else { // not a valid directory
                System.out.println("-bash: cd: " + arg + ": No such file or directory\n");
            }
        } else { // part of the path
            String newPath;
            if (workingDirectory.charAt(workingDirectory.length() - 1) != '/') {
                newPath = workingDirectory + '/' + arg;
            } else newPath = workingDirectory + arg;
            if (new File(newPath).isFile()) {
                try(FileWriter fw = new FileWriter(newPath, true)) { // true will append new string
                    for (String s : write)
                        fw.write(s); //appends the string
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("-bash: cd: " + arg + ": No such file or directory\n");
            }
        }
    }
}
