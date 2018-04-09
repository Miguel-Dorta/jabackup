/*
 * Copyright (C) 2018 Miguel Dorta
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.migueldorta.jabackup.utils;

import java.util.stream.IntStream;

/**
 * @author Miguel Dorta
 */
public class SweetStringBuilder {

    protected StringBuilder sb;

    // StringBuilder Constructors
    public SweetStringBuilder() {
        sb = new StringBuilder();
    }

    public SweetStringBuilder(int capacity) {
        sb = new StringBuilder(capacity);
    }

    public SweetStringBuilder​(CharSequence seq) {
        sb = new StringBuilder(seq);
    }

    public SweetStringBuilder(String str) {
        sb = new StringBuilder(str);
    }

    // StringBuilder Methods
    public SweetStringBuilder append(boolean b) {
        sb.append(b);
        return this;
    }

    public SweetStringBuilder append(char c) {
        sb.append(c);
        return this;
    }

    public SweetStringBuilder append(char[] str) {
        sb.append(str);
        return this;
    }

    public SweetStringBuilder append(char[] str, int offset, int len) {
        sb.append(str, offset, len);
        return this;
    }

    public SweetStringBuilder append(CharSequence s) {
        sb.append(s);
        return this;
    }

    public SweetStringBuilder append(CharSequence s, int start, int end) {
        sb.append(s, start, end);
        return this;
    }

    public SweetStringBuilder append(double d) {
        sb.append(d);
        return this;
    }

    public SweetStringBuilder append(float f) {
        sb.append(f);
        return this;
    }

    public SweetStringBuilder append(int i) {
        sb.append(i);
        return this;
    }

    public SweetStringBuilder append(long lng) {
        sb.append(lng);
        return this;
    }

    public SweetStringBuilder append(Object obj) {
        sb.append(obj);
        return this;
    }

    public SweetStringBuilder append(String str) {
        sb.append(str);
        return this;
    }

    public SweetStringBuilder append(StringBuffer strb) {
        sb.append(strb);
        return this;
    }

    public SweetStringBuilder appendCodePoint(int codePoint) {
        sb.appendCodePoint(codePoint);
        return this;
    }

    public int capacity() {
        return sb.capacity();
    }

    public char charAt(int index) {
        return sb.charAt(index);
    }

    public int codePointAt(int index) {
        return sb.codePointAt(index);
    }

    public int codePointBefore(int index) {
        return sb.codePointBefore(index);
    }

    public int codePointCount(int beginIndex, int endIndex) {
        return sb.codePointCount(beginIndex, endIndex);
    }

    public SweetStringBuilder delete(int start, int end) {
        sb.delete(start, end);
        return this;
    }

    public SweetStringBuilder deleteCharAt(int index) {
        sb.deleteCharAt(index);
        return this;
    }

    public void ensureCapacity(int minimumCapacity) {
        sb.ensureCapacity(minimumCapacity);
    }

    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        sb.getChars(srcBegin, srcEnd, dst, dstBegin);
    }

    public int indexOf(String str) {
        return sb.indexOf(str);
    }

    public int indexOf(String str, int fromIndex) {
        return sb.indexOf(str, fromIndex);
    }

    public SweetStringBuilder insert(int offset, boolean b) {
        sb.insert(offset, b);
        return this;
    }

    public SweetStringBuilder insert(int offset, char c) {
        sb.insert(offset, c);
        return this;
    }

    public SweetStringBuilder insert(int offset, char[] str) {
        sb.insert(offset, str);
        return this;
    }

    public SweetStringBuilder insert(int index, char[] str, int offset, int len) {
        sb.insert(index, str, offset, len);
        return this;
    }

    public SweetStringBuilder insert(int dstOffset, CharSequence s) {
        sb.insert(dstOffset, s);
        return this;
    }

    public SweetStringBuilder insert(int dstOffset, CharSequence s, int start, int end) {
        sb.insert(dstOffset, s, start, end);
        return this;
    }

    public SweetStringBuilder insert(int offset, double d) {
        sb.insert(offset, d);
        return this;
    }

    public SweetStringBuilder insert(int offset, float f) {
        sb.insert(offset, f);
        return this;
    }

    public SweetStringBuilder insert(int offset, int i) {
        sb.insert(offset, i);
        return this;
    }

    public SweetStringBuilder insert(int offset, long l) {
        sb.insert(offset, l);
        return this;
    }

    public SweetStringBuilder insert(int offset, Object obj) {
        sb.insert(offset, obj);
        return this;
    }

    public SweetStringBuilder insert(int offset, String str) {
        sb.insert(offset, str);
        return this;
    }

    public int lastIndexOf(String str) {
        return sb.lastIndexOf(str);
    }

    public int lastIndexOf(String str, int fromIndex) {
        return sb.lastIndexOf(str, fromIndex);
    }

    public int length() {
        return sb.length();
    }

    public int offsetByCodePoints(int index, int codePointOffset) {
        return sb.offsetByCodePoints(index, codePointOffset);
    }

    public SweetStringBuilder replace(int start, int end, String str) {
        sb.replace(start, end, str);
        return this;
    }

    public SweetStringBuilder reverse() {
        sb.reverse();
        return this;
    }

    public void setCharAt(int index, char ch) {
        sb.setCharAt(index, ch);
    }

    public void setLength(int newLength) {
        sb.setLength(newLength);
    }

    public CharSequence subSequence(int start, int end) {
        return sb.subSequence(start, end);
    }

    public String substring(int start) {
        return sb.substring(start);
    }

    public String substring(int start, int end) {
        return sb.substring(start, end);
    }

    @Override
    public String toString() {
        return sb.toString();
    }

    public void trimToSize() {
        sb.trimToSize();
    }

    public IntStream chars() {
        return sb.chars();
    }

    public IntStream codePoints() {
        return sb.codePoints();
    }

    // Syntactic sugar Methods
    public SweetStringBuilder append(String... str) {
        for (String s : str) {
            sb.append(s);
        }
        return this;
    }

    public SweetStringBuilder append(Object... obj) {
        for (Object o : obj) {
            sb.append(o.toString());
        }
        return this;
    }

}
