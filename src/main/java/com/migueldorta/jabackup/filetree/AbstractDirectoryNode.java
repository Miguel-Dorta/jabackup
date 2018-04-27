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

import com.migueldorta.jabackup.Main;
import com.migueldorta.jabackup.utils.RelativePath;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public abstract class AbstractDirectoryNode extends AbstractNode {

    protected static final boolean FOLLOW_SLINKS = Main.getFollowSymbolicLinks(),
            ADD_HIDDEN = Main.getAddHiddenFiles();
    protected ArrayList<AbstractNode> children;

    public AbstractDirectoryNode(File f) {
        super(f);
        children = new ArrayList<>();
    }

    @Override
    public void initialize() {
        File[] fa = f.listFiles();
        children.ensureCapacity(fa.length);

        for (File child : fa) {
            if ((!Files.isSymbolicLink(child.toPath()) || FOLLOW_SLINKS) && (!child.isHidden() || ADD_HIDDEN)) {
                if (child.isDirectory()) {
                    children.add(new DirectoryNode(child, this));
                } else if (child.isFile()) {
                    children.add(new FileNode(child, this));
                }
            }
        }
        initializeChilds();
    }

    protected void initializeChilds() {
        children.forEach((child) -> child.initialize());
    }

    @Override
    public void create() {
        File newF = new File(getRoot().f, getRelativePath());
        if (VERBOSE) {
            System.out.println("[Mkdir] " + newF);
        }
        newF.mkdirs();
        children.forEach((child) -> child.create());
    }

    /* This will largely improve performance if the paths were objects that
    internally work with String[], but it requires a serious refactor */
    public void addEntry(FileNode fn, String newPath) {
        boolean resultFound = false;
        for (AbstractNode child : children) {
            if (RelativePath.compare(child.getRelativePath(), newPath) < 0 && child instanceof DirectoryNode) {
                ((DirectoryNode) child).addEntry(fn, newPath);
                resultFound = true;
                break;
            }
        }
        if (!resultFound) {
            String thisPath = getRelativePath();
            if ((thisPath + RelativePath.getElementAt(newPath, -1)).equals(newPath)) {
                fn.father = this;
                children.add(fn);
            } else {
                DirectoryNode subDirectory = new DirectoryNode(new File(f, RelativePath.getElementAt(newPath, RelativePath.getNumberOfElements(thisPath) + 1)), this);
                children.add(subDirectory);
                subDirectory.addEntry(fn, newPath);
            }
        }
    }

}
