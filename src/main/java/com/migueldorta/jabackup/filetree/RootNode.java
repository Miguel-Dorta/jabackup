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

    HashMap<String, AbstractNode> treeFiles;

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

    protected void recordEntry(AbstractNode an) {
        treeFiles.put(an.getRelativePath(), an);
    }

}
