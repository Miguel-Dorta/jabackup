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

    public boolean addEntry(FileNode fn, String newPath) {
        String thisPath = getRelativePath();
        if (newPath.startsWith(thisPath)) {
            boolean found = false;
            for (AbstractNode child : children) {
                if (child instanceof DirectoryNode) {
                    found = ((DirectoryNode) child).addEntry(fn, newPath);
                    if (found) {
                        break;
                    }
                }
            }

            if (!found) {
                String[] subpath = subpath(newPath).split(File.separator);
                if (subpath.length == 1) {
                    fn.father = this;
                    children.add(fn);
                } else {
                    DirectoryNode dn = new DirectoryNode(new File(f, subpath[0]), this);
                    children.add(dn);
                    dn.addEntry(fn, newPath);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    protected String subpath(String newPath) {
        String[] thisArrayPath = getRelativePath().split(File.separator),
                newArrayPath = newPath.split(File.separator);
        int thisPathLength = thisArrayPath[0].equals("") ? 0 : thisArrayPath.length;
        StringBuilder sb = new StringBuilder((newArrayPath.length - thisPathLength) * 20);
        for (int i = thisPathLength; i < newArrayPath.length; i++) {
            sb.append(newArrayPath[i]);
            sb.append(File.separator);
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    @Override
    public boolean rmEntry(String rmPath) {
        String thisPath = getRelativePath();
        if (rmPath.startsWith(thisPath)) {
            if (rmPath.equals(thisPath)) {
                rm();
                return true;
            } else {
                boolean found = false;
                for (AbstractNode child : children) {
                    found = child.rmEntry(rmPath);
                    if (found) {
                        children.remove(child);
                        break;
                    }
                }
                return found;
            }
        } else {
            return false;
        }
    }

    public FileNode getFile(String path) {
        String thisPath = getRelativePath();
        for (AbstractNode child : children) {
            if (path.startsWith(child.getRelativePath())) {
                if (child instanceof AbstractDirectoryNode) {
                    return ((AbstractDirectoryNode) child).getFile(path);
                } else {
                    return (FileNode) child;
                }
            }
        }
        return null;
    }

    @Override
    public void rm() {
        super.rm();
        children.forEach((child) -> child.rm());
        children = null;
    }

}
