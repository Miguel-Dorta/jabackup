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

import java.util.Calendar;

public class Date implements Comparable {

    private long year;
    private byte month, day, hour, minute;

    public Date(long year, byte month, byte day, byte hour, byte minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public Date() {
        Calendar c = Calendar.getInstance();
        this.year = c.get(Calendar.YEAR);
        this.month = (byte) (c.get(Calendar.MONTH) + 1);
        this.day = (byte) c.get(Calendar.DAY_OF_MONTH);
        this.hour = (byte) c.get(Calendar.HOUR_OF_DAY);
        this.minute = (byte) c.get(Calendar.MINUTE);
    }

    @Override
    public String toString() {
        return year + "-" + month + "-" + day + "_" + hour + "-" + minute;
    }

    @Override
    public int compareTo(Object t) {
        Date d = (Date) t;
        int x = Long.compare(year, d.year);
        if (x == 0) {
            x = month - d.month;
            if (x == 0) {
                x = day - d.day;
                if (x == 0) {
                    x = hour - d.hour;
                    if (x == 0) {
                        return minute - d.minute;
                    } else {
                        return x;
                    }
                } else {
                    return x;
                }
            } else {
                return x;
            }
        } else {
            return x;
        }
    }

}
