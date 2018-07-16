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

import com.orange.gradle.internal.Dependencies;

@SuppressWarnings("unused")
public final class Plugins extends Dependencies {

    // region Versions

    private static final String androidStudioVersion = "3.1.3";
    public static final String detektVersion = "1.0.0.RC7";
    private static final String dokkaVersion = "0.9.17";
    private static final String errorProneVersion = "0.0.14";
    private static final String googleServices = "4.0.1";

    // endregion

    public static final String androidStudio = "com.android.tools.build:gradle:" + androidStudioVersion;
    public static final String dokka = "org.jetbrains.dokka:dokka-android-gradle-plugin:" + dokkaVersion;
    public static final String kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:" + kotlinVersion;
    public static final String google = "com.google.gms:google-services:" + googleServices;

    // FIXME : When Gradle DSL fix the variable in plugin, use Dependencies.Plugins
    @Deprecated
    public static final String detekt = "gradle.plugin.io.gitlab.arturbosch.detekt:detekt-gradle-plugin:" + detektVersion;
    @Deprecated
    public static final String errorProne = "net.ltgt.errorprone:" + errorProneVersion;
}
