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

import com.migueldorta.jabackup.filesystem.Directory;

/**
 * @author Miguel Dorta
 */
public abstract class Main {

    private static String ib, ob;
    private static boolean verbose;

    public static void main(String args[]) {
        ib = null;
        ob = null;
        verbose = false;

        readArgs(args);
        if (ib != null && ob != null) {
            createOriginTree();
        }
    }

    public static boolean getVerbose() {
        return verbose;
    }

    private static void createOriginTree() {
        System.out.println(":: Saving origin file tree...");
        Directory origin = new Directory(ib);
        System.out.println("Done!");
    }

    private static void readArgs(String args[]) {
        if (args.length == 0) {
            printHelp();
        } else {
            try {
                for (String arg : args) {
                    if (arg.charAt(0) == '-') {
                        if (arg.charAt(1) == '-') {
                            switch (arg.substring(2)) {
                                case "help":
                                    printHelp();
                                    break;
                                case "update":
                                    updateJabackup();
                                    break;
                                case "verbose":
                                    verbose = true;
                                    break;
                                case "version":
                                    printVersion();
                                    break;
                                default:
                                    throw new Exception("Invalid argument: " + arg);
                            }
                        } else {
                            for (int i = 1; i < arg.length(); i++) {
                                switch (arg.charAt(i)) {
                                    case 'h':
                                        printHelp();
                                        break;
                                    case 'u':
                                        updateJabackup();
                                    case 'v':
                                        verbose = true;
                                        break;
                                    default:
                                        throw new Exception("Invalid argument: " + arg);
                                }
                            }
                        }
                    } else {
                        if (arg.startsWith("ib=")) {
                            ib = arg.substring(3);
                        } else if (arg.startsWith("ob=")) {
                            ob = arg.substring(3);
                        } else {
                            throw new Exception("Invalid argument: " + arg);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(64);
            }
        }
    }

    private static void printHelp() {
        System.out.println(":: jabackup help ::\nHow to use:   java -jar jabackup.jar [args]\n\nARGUMENTS:\n  ib=<path>      Define the directory to make a backup of.\n  ob=<path>      Define the directory to store the backups.\n  -h, --help     Shows this message.\n  -u, --update   Updates jabackup at the latest stable version.\n  -v, --verbose  Displays detailled information.\n  --version      Displays jabackup version.\n\nExample:\n  java -jar jabackup.jar ib=/home/myuser/ ob=/mnt/backups/jabackup/\n  java -jar jabackup.jar --update\n\nNotes:\n  Arguments like \"--help\", \"--update\" or \"--version\" will stop the\n  execution of the program even if there are other parameters defined.");
        System.exit(0);
    }

    private static void printVersion() {
        System.out.println("Not supported yet");
        System.exit(0);
    }

    private static void updateJabackup() {
        System.out.println("Not supported yet");
        System.exit(0);
    }
}
