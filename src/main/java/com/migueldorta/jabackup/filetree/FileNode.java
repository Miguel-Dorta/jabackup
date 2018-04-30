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
import com.migueldorta.jabackup.exceptions.CodeErrorException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileNode extends AbstractNode {

    protected AbstractDirectoryNode father;
    protected byte[] md5;

    public FileNode(File f, AbstractDirectoryNode father) {
        super(f);
        this.father = father;
    }

    @Override
    public void create() {
        try {
            File newF = new File(getRoot().f, getRelativePath());
            if (VERBOSE) {
                System.out.println("[Copying] " + f + " to " + newF);
            }
            Files.copy(f.toPath(), newF.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
        } catch (IOException ex) {
            System.out.println("[WARNING] Error while copying " + f);
        }
    }

    @Override
    public void initialize() {
        father.getRoot().recordEntry(this);
    }

    @Override
    public String getRelativePath() {
        return father.getRelativePath() + f.getName();
    }

    @Override
    public RootNode getRoot() {
        return father.getRoot();
    }

    @Override
    public void rm() {
        super.rm();
        father = null;
        md5 = null;
    }

    @Override
    public boolean rmEntry(String rmPath) {
        if (rmPath.equals(getRelativePath())) {
            rm();
            return true;
        } else {
            return false;
        }
    }

    public String getFileName() {
        return f.getName();
    }

    public byte[] getSHA1() throws Exception {
        return getChecksum("SHA-1", f);
    }

    public byte[] getMD5() throws Exception {
        if (md5 == null) {
            md5 = getChecksum("MD5", f);
        }
        return md5;
    }

    private static byte[] getChecksum(String algorithm, File file) throws Exception {
        MessageDigest md;
        try (FileInputStream fis = new FileInputStream(file)) {
            try {
                md = MessageDigest.getInstance(algorithm);
            } catch (NoSuchAlgorithmException e) {
                throw new CodeErrorException("NoSuchAlgorithmException with " + algorithm);
            }
            byte[] byteArray = new byte[1024];

            int bytesCount = 0;
            while ((bytesCount = fis.read(byteArray)) != -1) {
                md.update(byteArray, 0, bytesCount);
            }
            return md.digest();
        } catch (SecurityException | IOException ex) {
            Main.addError("Error while reading " + file + ": " + ex.getClass().getCanonicalName());
            throw ex;
        } catch (CodeErrorException cee) {
            Main.addError(cee.getMessage());
            throw cee;
        } catch (Exception e) {
            CodeErrorException ce = new CodeErrorException("Uncaught exception in FileNode.getChecksum(): " + e.getClass().getCanonicalName());
            Main.addError(ce.getMessage());
            throw ce;
        }
    }
}
