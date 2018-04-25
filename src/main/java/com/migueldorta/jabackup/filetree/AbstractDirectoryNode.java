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
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public abstract class AbstractDirectoryNode extends AbstractNode {

    protected static final boolean FOLLOW_SLINKS = Main.getFollowSymbolicLinks(),
            ADD_HIDDEN = Main.getAddHiddenFiles();
    protected ArrayList<AbstractNode> children;

    public AbstractDirectoryNode(File f) {
        super(f);
    }

    @Override
    public void initialize() {
        File[] fa = f.listFiles();
        children = new ArrayList<>(fa.length);

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

    public void addEntry(FileNode fn, String newPath) {
        boolean resultFound = false;
        for (AbstractNode child : children) {
            if (child instanceof DirectoryNode && newPath.startsWith(child.getRelativePath())) {
                ((DirectoryNode) child).addEntry(fn, newPath);
                resultFound = true;
                break;
            }
        }
        if (!resultFound) { //CHANGE THIS!!!!!
            String[] newPathArray = newPath.split(File.separator);
            int nameIndex = (int) getRelativePath().codePoints().filter(ch -> ch == File.separatorChar).count();
            DirectoryNode subDirectory = new DirectoryNode(new File(f, newPathArray[nameIndex]), this);
            children.add(subDirectory);
            subDirectory.addEntry(fn, newPath);
        }
    }

}
