/*
 * Copyright (c) 2018 Voonder
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.orange.gradle;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SuppressWarnings("unused")
public final class AppVersion {

    private static final int[] versions = new int[]{1, 0, 0};

    public static final Integer code = formatVersion(Type.CODE);
    public static final Integer database = formatVersion(Type.DATABASE);
    public static final String name = formatVersion(Type.NAME);
    public static final String snapshot = formatVersion(Type.SNAPSHOT);

    private enum Type {
        CODE, DATABASE, NAME, SNAPSHOT
    }

    private static final int majorMultiple = 1000000;
    private static final int minorMultiple = 1000;

    @SuppressWarnings("unchecked")
    private static <T> T formatVersion(Type type) {
        if (versions.length != 3) {
            throw new RuntimeException("Application version must be an array with 3 value for MAJOR.MINOR.PATCH format.");
        }

        switch (type) {
            case CODE:
            case DATABASE:
                return (T) Integer.valueOf(versions[0] * majorMultiple + versions[1] * minorMultiple + versions[2]);
            case NAME:
                return (T) IntStream.of(versions)
                        .mapToObj(Integer::toString)
                        .collect(Collectors.joining("."));
            case SNAPSHOT:
                String version = IntStream.of(versions)
                        .mapToObj(Integer::toString)
                        .collect(Collectors.joining("."));
                return (T) String.valueOf(version + "-SNAPSHOT");
            default:
                return null;
        }
    }
}
