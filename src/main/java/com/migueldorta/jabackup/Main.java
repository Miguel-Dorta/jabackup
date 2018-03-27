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

import com.migueldorta.jabackup.filesystem.DirectoryFS;
import com.migueldorta.jabackup.utils.Date;
import java.io.File;

/**
 * @author Miguel Dorta
 */
public abstract class Main {

    private static String input, output;
    private static boolean verbose, followSymbolicLinks, addHiddenFiles;
    private static int frequency;

    public static void main(String args[]) {
        input = null;
        output = null;
        verbose = false;
        followSymbolicLinks = false;
        addHiddenFiles = false;

        int exitCode = ArgumentReader.readArgs(args);
        if (exitCode >= 0) {
            System.exit(exitCode);
        }

        OutputSettings os = new OutputSettings(output);

        System.out.println(":: Saving origin file tree...");
        DirectoryFS origin = new DirectoryFS(input);
        System.out.println("Done!");
        File destiny = new File(output, new Date().toString() + "_jabackup");

        if (os.isEmpty() || os.isTheNextBackupFull()) {
            //Copy the origin tree to destiny
            //Generate BackupSettings.ini in destiny
        } else {
            //Obtain combined trees from previous backup
            //Compare origin with combined previous trees
            //Copy the result to destiny
            //Create a changelog in destiny
            //Create a BackupSettings.ini in destiny
        }
    }

    public static int createFullBackup() {
        System.out.println("Not supported yet");
        return 0;
    }

    public static int updateJabackup() {
        System.out.println("Not supported yet");
        return 0;
    }

    public static boolean getAddHiddenFiles() {
        return addHiddenFiles;
    }

    public static boolean getFollowSymbolicLinks() {
        return followSymbolicLinks;
    }

    public static String getInput() {
        return input;
    }

    public static String getOutput() {
        return output;
    }

    public static boolean getVerbose() {
        return verbose;
    }

    public static void setAddHiddenFiles(boolean b) {
        addHiddenFiles = b;
    }

    public static void setFollowSymbolicLinks(boolean b) {
        followSymbolicLinks = b;
    }

    public static void setInput(String s) {
        input = s;
    }

    public static void setOutput(String s) {
        output = s;
    }

    public static void setVerbose(boolean b) {
        verbose = b;
    }

    public static void setFrequency(int i) {
        frequency = i;
    }

}
