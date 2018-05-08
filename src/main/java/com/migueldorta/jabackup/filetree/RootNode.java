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
package com.migueldorta.jabackup.filetree;

import java.io.File;
import java.util.HashMap;

public class RootNode extends AbstractDirectoryNode {

    HashMap<String, FileNode> treeFiles;

    public RootNode(File f) {
        super(f);
        treeFiles = new HashMap<>(200);
    }

    public RootNode(String s) {
        this(new File(s));
    }

    @Override
    public RootNode getRoot() {
        return this;
    }

    @Override
    public String getRelativePath() {
        return "";
    }

    @Override
    public void rm() {
        super.rm();
        treeFiles = null;
    }

    @Override
    protected void initializeChilds() {
        rmEntry("BackupSettings.ini");
        super.initializeChilds();
    }

    protected void recordEntry(FileNode fn) {
        treeFiles.put(fn.getRelativePath(), fn);
    }

    public void rmRecord(FileNode fn) {
        treeFiles.remove(fn.getRelativePath(), fn);
    }

    public void compare(RootNode rn) {
        rn.treeFiles.entrySet().parallelStream().forEach(e -> {
            String s2 = e.getKey();
            FileNode fn2 = e.getValue();

            if (treeFiles.containsKey(s2)) { // s2 are the paths that exists in both rn and this
                FileNode fn1 = treeFiles.get(s2);
                try {
                    if (FileNode.haveSameChecksum(fn1, fn2)) {
                        // NOT MODIFIED
                    } else {
                        // MODIFIED
                    }
                } catch (Exception ex) {
                    /* This exception is correctly catched in FileNode. It's
                    thrown to know that the hash is impossible to get, so it
                    avoids unespected behaviour */
                }
            } else { // s2 are only the paths that exists in rn, but not in this object
                treeFiles.forEach((s1, fn1) -> {
                    try {
                        if (FileNode.haveSameChecksum(fn1, fn2)) {
                            // COPIED
                        } else {
                            // ADDED
                        }
                    } catch (Exception ex) {
                        /* This exception is correctly catched in FileNode. It's
                        thrown to know that the hash is impossible to get, so it
                        avoids unespected behaviour */
                    }
                });
            }
        });

        treeFiles.entrySet().parallelStream().forEach(s -> {
            if (rn.treeFiles.containsKey(s.getKey())) {
                // REMOVED
            }
        });

    }
}
