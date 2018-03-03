/*
 * The MIT License
 *
 * Copyright 2018 Miguel Dorta.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.migueldorta.jabackup;

import com.migueldorta.jabackup.exceptions.InvalidArgumentException;
import com.migueldorta.jabackup.filesystem.Directory;

/**
 * @author Miguel Dorta
 */
public abstract class Main {

    private static String ib, ob;
    private static boolean verbose;
    private static int every;

    public static void main(String args[]) {
        ib = null;
        ob = null;
        verbose = false;

        int exitCode = readArgs(args);
        if (exitCode > 0) {
            System.exit(exitCode);
        }
        createOriginTree();
    }

    public static boolean getVerbose() {
        return verbose;
    }

    private static void createOriginTree() {
        System.out.println(":: Saving origin file tree...");
        Directory origin = new Directory(ib);
        System.out.println("Done!");
    }

    private static int readArgs(String args[]) {
        if (args.length == 0) {
            return printHelp();
        } else {
            try {
                for (String arg : args) {
                    if (arg.charAt(0) == '-') {
                        if (arg.charAt(1) == '-') {
                            switch (arg.substring(2)) {
                                case "full":
                                    return createFullBackup();
                                case "help":
                                    return printHelp();
                                case "update":
                                    return updateJabackup();
                                case "verbose":
                                    verbose = true;
                                    break;
                                case "version":
                                    return printVersion();
                                default:
                                    throw new InvalidArgumentException(arg);
                            }
                        } else if (arg.charAt(1) != 'X') {
                            for (int i = 1; i < arg.length(); i++) {
                                switch (arg.charAt(i)) {
                                    case 'f':
                                        return createFullBackup();
                                    case 'h':
                                        return printHelp();
                                    case 'u':
                                        return updateJabackup();
                                    case 'v':
                                        verbose = true;
                                        break;
                                    default:
                                        throw new InvalidArgumentException(arg);
                                }
                            }
                        }
                    } else {
                        if (arg.startsWith("ib=")) {
                            ib = arg.substring(3);
                        } else if (arg.startsWith("ob=")) {
                            ob = arg.substring(3);
                        } else if (arg.startsWith("every=")) {
                            every = Integer.parseInt(arg.substring(6));
                        } else {
                            throw new InvalidArgumentException(arg);
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Error parsing number");
                return 1;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error parsing a parameter");
                return 1;
            } catch (InvalidArgumentException e) {
                System.out.println(e.getMessage());
                return 1;
            }
            if (ib == null || ob == null) {
                System.out.println("You need to define both, \"ib=\" and \"ob=\"");
                return 1;
            }
            return -1;
        }
    }

    private static int createFullBackup() {
        System.out.println("Not supported yet");
        return 0;
    }

    private static int printHelp() {
        System.out.println(":: jabackup help ::\nHow to use:   java -jar jabackup.jar [args]\n\nARGUMENTS:\n  every=<int>    Define the frequency to create full backups. 0 to\n                 disable. 8 as default.\n  ib=<path>      Define the directory to make a backup of.\n  ob=<path>      Define the directory to store the backups.\n  -f, --full     Creates a full backup instead of incremental.\n  -h, --help     Shows this message.\n  -u, --update   Updates jabackup at the latest stable version.\n  -v, --verbose  Displays detailled information.\n  --version      Displays jabackup version.\n\nExample:\n  java -jar jabackup.jar ib=/home/myuser/ ob=/mnt/backups/jabackup/\n  java -jar jabackup.jar --update\n\nNotes:\n  Arguments like \"--help\", \"--update\" or \"--version\" will stop the\n  execution of the program even if there are other parameters defined.\n\n  The \"every\" argument is preserved between backups. The previous\n  value will be overwritten when you define it.\n");
        return 0;
    }

    private static int printVersion() {
        System.out.println("Not supported yet");
        return 0;
    }

    private static int updateJabackup() {
        System.out.println("Not supported yet");
        return 0;
    }
}
