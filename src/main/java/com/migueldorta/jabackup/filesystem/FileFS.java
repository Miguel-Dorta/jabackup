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
package com.migueldorta.jabackup.filesystem;

import com.migueldorta.jabackup.exceptions.CodeErrorException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileFS extends ObjectFS {

    byte[] md5;

    public FileFS(String path) {
        super(path);
        md5 = null;
    }

    public byte[] getSHA1() throws CodeErrorException, IOException {
        return getChecksum("SHA-1", this);
    }

    public byte[] getMD5() throws CodeErrorException, IOException {
        if (md5 == null) {
            md5 = getChecksum("MD5", this);
        }
        return md5;
    }

    private static byte[] getChecksum(String algorithm, File file) throws CodeErrorException, IOException {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new CodeErrorException("NoSuchAlgorithmException with " + algorithm);
        }
        FileInputStream fis = new FileInputStream(file);
        byte[] byteArray = new byte[1024];

        int bytesCount = 0;
        while ((bytesCount = fis.read(byteArray)) != -1) {
            md.update(byteArray, 0, bytesCount);
        }
        fis.close();

        return md.digest();
    }

}
