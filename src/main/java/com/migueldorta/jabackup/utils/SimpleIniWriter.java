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
package com.migueldorta.jabackup.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SimpleIniWriter extends SimpleINI {

    public SimpleIniWriter(File f) {
        super(f);
    }

    public void add(String key, String value) {
        hm.put(key, value);
    }

    public void write(int size) throws IOException {
        try (FileWriter fw = new FileWriter(f)) {
            SweetStringBuilder ssb = new SweetStringBuilder(size);
            hm.forEach((String key, String value) -> {
                ssb.append(key, "=", value, System.lineSeparator());
            });
            fw.write(ssb.toString());
        }
    }

    public void write() throws IOException {
        write(200);
    }

}
