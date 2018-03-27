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

import com.migueldorta.jabackup.filesystem.BackupFolder;
import java.io.File;
import java.util.ArrayList;

public class OutputSettings {

    ArrayList<BackupFolder> list;
    File output;

    public OutputSettings(String outputPath) {
        System.out.println(":: Gathering previous backups settings...");
        output = new File(outputPath);
        list = calcArrayList(output);
        System.out.println("Done!");
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public boolean isTheNextBackupFull() {
        int frequency = Integer.MAX_VALUE, backupsSinceLastFull = 0;
        for (BackupFolder bf : list) {
            backupsSinceLastFull = bf.isFullBackup() ? 0 : backupsSinceLastFull + 1;
            if (frequency != bf.getFrequency()) {
                frequency = bf.getFrequency();
            }
        }
        return backupsSinceLastFull >= frequency;
    }

    private static ArrayList<BackupFolder> calcArrayList(File output) {
        File[] foldersInOutput = output.listFiles();
        ArrayList<BackupFolder> al = new ArrayList(foldersInOutput.length);
        for (File file : foldersInOutput) {
            BackupFolder bf = new BackupFolder(file);
            if (bf.isValid()) {
                if (Main.getVerbose()) {
                    System.out.println("[Detected] Previous backup in " + file.getPath());
                }
                al.add(bf);
            }
        }
        al.sort((BackupFolder bf1, BackupFolder bf2) -> {
            return bf1.getDate().compareTo(bf2.getDate());
        });
        return al;
    }

}
