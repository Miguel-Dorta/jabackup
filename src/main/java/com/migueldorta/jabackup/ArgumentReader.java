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

public abstract class ArgumentReader {

    public static final String INPUT = "ib=",
            OUTPUT = "ob=",
            FREQUENCY = "every=",
            FULL_STRING = "full",
            HELP_STRING = "help",
            UPDATE_STRING = "update",
            VERBOSE_STRING = "verbose",
            VERSION_STRING = "version";

    public static final char FULL_CHAR = 'f',
            HELP_CHAR = 'h',
            UPDATE_CHAR = 'u',
            VERBOSE_CHAR = 'v';

    public static int readArgs(String args[]) {
        if (args.length == 0) {
            return printHelp();
        } else {
            try {
                for (String arg : args) {
                    if (arg.charAt(0) == '-') {
                        if (arg.charAt(1) == '-') {
                            switch (arg.substring(2)) {
                                case FULL_STRING:
                                    return Main.createFullBackup();
                                case HELP_STRING:
                                    return printHelp();
                                case UPDATE_STRING:
                                    return Main.updateJabackup();
                                case VERBOSE_STRING:
                                    Main.setVerbose(true);
                                    break;
                                case VERSION_STRING:
                                    return Main.printVersion();
                                default:
                                    throw new InvalidArgumentException(arg);
                            }
                        } else if (arg.charAt(1) != 'X') {
                            for (int i = 1; i < arg.length(); i++) {
                                switch (arg.charAt(i)) {
                                    case FULL_CHAR:
                                        return Main.createFullBackup();
                                    case HELP_CHAR:
                                        return printHelp();
                                    case UPDATE_CHAR:
                                        return Main.updateJabackup();
                                    case VERBOSE_CHAR:
                                        Main.setVerbose(true);
                                        break;
                                    default:
                                        throw new InvalidArgumentException(arg);
                                }
                            }
                        }
                    } else {
                        if (arg.startsWith(INPUT)) {
                            Main.setInput(arg.substring(3));
                        } else if (arg.startsWith(OUTPUT)) {
                            Main.setOutput(arg.substring(3));
                        } else if (arg.startsWith(FREQUENCY)) {
                            Main.setFrequency(Integer.parseInt(arg.substring(6)));
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
            if (Main.getInput() == null || Main.getOutput() == null) {
                System.out.println("You need to define both, \"" + INPUT + "\" and \"" + OUTPUT + "\"");
                return 1;
            }
            return -1;
        }
    }

    private static int printHelp() {
        StringBuilder sb = new StringBuilder(":: jabackup help ::");
        sb.append("\nHow to use:   java -jar jabackup.jar [args]");
        sb.append("\n\nARGUMENTS:");

        sb.append("\n  ");
        sb.append(FREQUENCY);
        sb.append("<int>    Define the frequency to create full backups. 0 to\n                 disable. 8 as default.");

        sb.append("\n  ");
        sb.append(INPUT);
        sb.append("<path>      Define the directory to make a backup of.");

        sb.append("\n  ");
        sb.append(OUTPUT);
        sb.append("<path>      Define the directory to store the backups.");

        sb.append("\n  -");
        sb.append(FULL_CHAR);
        sb.append(", --");
        sb.append(FULL_STRING);
        sb.append("     Creates a full backup instead of incremental.");

        sb.append("\n  -");
        sb.append(HELP_CHAR);
        sb.append(", --");
        sb.append(HELP_STRING);
        sb.append("     Shows this message.");

        sb.append("\n  -");
        sb.append(UPDATE_CHAR);
        sb.append(", --");
        sb.append(UPDATE_STRING);
        sb.append("   Updates jabackup at the latest stable version.");

        sb.append("\n  -");
        sb.append(VERBOSE_CHAR);
        sb.append(", --");
        sb.append(VERBOSE_STRING);
        sb.append("  Displays detailled information.");

        sb.append("\n  --");
        sb.append(VERSION_STRING);
        sb.append("      Displays jabackup version.");

        sb.append("\n\nExample:");

        sb.append("\n  java -jar jabackup.jar ");
        sb.append(INPUT);
        sb.append("/home/myuser/");
        sb.append(" ");
        sb.append(OUTPUT);
        sb.append("/mnt/backups/jabackup/");

        sb.append("\n  java -jar jabackup.jar --");
        sb.append(UPDATE_STRING);

        sb.append("\n\nNotes:");

        sb.append("\n  Arguments like \"--");
        sb.append(HELP_STRING);
        sb.append("\", \"--");
        sb.append(UPDATE_STRING);
        sb.append("\" or \"--");
        sb.append(VERSION_STRING);
        sb.append("\" will stop the\n  execution of the program even if there are other parameters defined.");

        sb.append("\n\n  The \"");
        sb.append(FREQUENCY);
        sb.append("\" argument is preserved between backups. The previous\n  value will be overwritten when you define it.");

        sb.append("\n");

        System.out.println(sb);
        return 0;
    }

}
