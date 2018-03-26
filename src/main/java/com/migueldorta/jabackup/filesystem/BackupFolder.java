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

import com.migueldorta.jabackup.utils.Date;
import com.migueldorta.jabackup.utils.SimpleINI;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BackupFolder {

    public static final Pattern BACKUP_FOLDER_PATTERN = Pattern.compile("(\\d{4,})-(\\d{2})-(\\d{2})_(\\d{2})-(\\d{2})_jabackup");
    private File f;
    private Matcher m;
    private Date d;
    private SimpleINI settings;

    public BackupFolder(File f) {
        this.f = f;
        m = BACKUP_FOLDER_PATTERN.matcher(f.toPath().getFileName().toString());
        if (m.matches()) {
            try {
                d = new Date(
                        Long.parseLong(m.group(1)),
                        Byte.parseByte(m.group(2)),
                        Byte.parseByte(m.group(3)),
                        Byte.parseByte(m.group(4)),
                        Byte.parseByte(m.group(5))
                );
                settings = new SimpleINI(new File(f, "BackupSettings.ini"));
            } catch (Exception e) {
                this.f = null;
            }
        } else {
            this.f = null;
        }
    }

    public Date getDate() {
        return d;
    }

    public boolean isValid() {
        return f != null;
    }

    public File getFile() {
        return f;
    }

    public boolean isFullBackup() {
        return Boolean.parseBoolean(settings.getValue("isfullbackup"));
    }

    public int getFrequency() {
        try {
            return Integer.parseInt(settings.getValue("frequency"));
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

}
