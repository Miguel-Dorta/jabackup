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
import com.migueldorta.jabackup.utils.SweetStringBuilder;

public abstract class ArgumentReader {

    public static final String LS = System.lineSeparator();

    public static final String INPUT = "ib=",
            OUTPUT = "ob=",
            FREQUENCY = "every=",
            FULL_STRING = "full",
            HELP_STRING = "help",
            HIDDEN_STRING = "hidden",
            SYMLINK_STRING = "link",
            UPDATE_STRING = "update",
            VERBOSE_STRING = "verbose",
            VERSION_STRING = "version";

    public static final char HIDDEN_CHAR = 'a',
            FULL_CHAR = 'f',
            HELP_CHAR = 'h',
            SYMLINK_CHAR = 'l',
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
                                case HIDDEN_STRING:
                                    Main.setAddHiddenFiles(true);
                                    break;
                                case SYMLINK_STRING:
                                    Main.setFollowSymbolicLinks(true);
                                    break;
                                case UPDATE_STRING:
                                    return Main.updateJabackup();
                                case VERBOSE_STRING:
                                    Main.setVerbose(true);
                                    break;
                                case VERSION_STRING:
                                    return printVersion();
                                default:
                                    throw new InvalidArgumentException(arg);
                            }
                        } else if (arg.charAt(1) != 'X') {
                            for (int i = 1; i < arg.length(); i++) {
                                switch (arg.charAt(i)) {
                                    case HIDDEN_CHAR:
                                        Main.setAddHiddenFiles(true);
                                        break;
                                    case FULL_CHAR:
                                        return Main.createFullBackup();
                                    case HELP_CHAR:
                                        return printHelp();
                                    case SYMLINK_CHAR:
                                        Main.setFollowSymbolicLinks(true);
                                        break;
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
        SweetStringBuilder ssb = new SweetStringBuilder(2048);

        ssb.append(
                ":: jabackup help ::", LS,
                "How to use:   java -jar jabackup.jar [args]", LS,
                LS,
                "ARGUMENTS:", LS,
                "  ", FREQUENCY, "<int>    Define the frequency to create full backups. 0 to", LS,
                "                 disable. 8 as default.", LS,
                "  ", INPUT, "<path>      Define the directory to make a backup of.", LS,
                "  ", OUTPUT, "<path>      Define the directory to store the backups.", LS,
                "  -", HIDDEN_CHAR, ", --", HIDDEN_STRING, "   Add the hidden files to the backup.", LS,
                "  -", FULL_CHAR, ", --", FULL_STRING, "     Creates a full backup instead of incremental.", LS,
                "  -", HELP_CHAR, ", --", HELP_STRING, "     Shows this message.", LS,
                "  -", SYMLINK_CHAR, ", --", SYMLINK_STRING, "     Follow symbolic links.", LS,
                "  -", UPDATE_CHAR, ", --", UPDATE_STRING, "   Updates jabackup at the latest stable version.", LS,
                "  -", VERBOSE_CHAR, ", --", VERBOSE_STRING, "  Displays detailed information.", LS,
                "  --", VERSION_STRING, "      Displays jabackup version.", LS,
                LS,
                "Example:", LS,
                "  java -jar jabackup.jar ", INPUT, "/home/myuser/", " ", OUTPUT, "/mnt/backups/jabackup/", LS,
                "  java -jar jabackup.jar --", UPDATE_STRING, LS,
                LS,
                "Notes:", LS,
                "  Arguments like \"--", HELP_STRING, "\", \"--", UPDATE_STRING, "\" or \"--", VERSION_STRING, "\" will stop the", LS,
                "  execution of the program even if there are other parameters defined.", LS,
                LS,
                "  The \"", FREQUENCY, "\" argument is preserved between backups. The previous", LS,
                "  value will be overwritten when you define it.", LS);

        System.out.println(ssb);
        return 0;
    }

    private static int printVersion() {
        SweetStringBuilder ssb = new SweetStringBuilder(300);

        ssb.append(
                "jabackup ", Main.class.getPackage().getImplementationVersion(), " - Copyright (c) 2018 Miguel Dorta.", LS,
                LS,
                "This work is licensed under the terms of the MIT license.", LS,
                "For a copy, see <https://opensource.org/licenses/MIT>", LS,
                LS,
                "jabackup is made possible by other open source software.", LS,
                "Visit <https://git.io/jabackup_credits> to learn more.", LS);

        System.out.println(ssb);
        return 0;
    }

}
