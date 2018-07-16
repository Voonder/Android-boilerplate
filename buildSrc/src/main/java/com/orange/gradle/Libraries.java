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

import java.util.HashMap;

@SuppressWarnings("unused")
public final class Libraries extends Dependencies {

    // region Versions

    private static final String androidVersion = "27.1.1";
    private static final String androidFixVersion = "27.1.1.1";
    private static final String androidArchVersion = "1.1.1";
    private static final String constraintLayoutVersion = "1.1.2";
    private static final String daggerVersion = "2.16";
    private static final HashMap<String, String> firebaseVersion = new HashMap<String, String>() {{
        put("core","16.0.1");
        put("messaging","17.0.0");
    }};
    private static final String glideVersion = "4.7.1";
    private static final String javaxInjectVersion = "1";
    private static final String moshiVersion = "1.6.0";
    private static final String okhttpVersion = "3.11.0";
    private static final String pagingVersion = "1.0.0";
    private static final String retrofitVersion = "2.4.0";
    private static final String roomVersion = "1.1.1";
    private static final String timberVersion = "4.7.1";

    // endregion

    // region Kotlin

    public static final String kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:" + kotlinVersion;
    public static final String kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:" + kotlinVersion;

    // endregion

    // region Android

    public static final String archExtensions = "android.arch.lifecycle:extensions:" + androidArchVersion;
    public static final String archRuntime = "android.arch.lifecycle:compiler:" + androidArchVersion;
    public static final String appCompatV7 = "com.android.support:appcompat-v7:" + androidVersion;
    public static final String cardView = "com.android.support:cardView-v7:" + androidVersion;
    public static final String constraintLayout = "com.android.support.constraint:constraint-layout:" + constraintLayoutVersion;
    public static final String recyclerView = "com.android.support:recyclerView-v7:" + androidVersion;
    public static final String paging = "android.arch.paging:runtime:" + pagingVersion;
    public static final String room = "android.arch.persistence.room:runtime:" + roomVersion;
    public static final String roomCompiler = "android.arch.persistence.room:compiler:" + roomVersion;
    public static final String supportDesign = "com.android.support:design:" + androidVersion;
    public static final String supportV4 = "com.android.support:support-v4:" + androidVersion;

    // endregion

    // region Preference Fix for Android

    public static final String preferenceV7 = "com.takisoft.fix:preference-v7:" + androidFixVersion;

    // endregion

    // region Firebase

    public static final String firebaseCore = "com.google.firebase:firebase-messaging:" + firebaseVersion.get("core");
    public static final String firebaseMessaging = "com.google.firebase:firebase-messaging:" + firebaseVersion.get("messaging");

    // endregion

    // region Third-party libraries

    public static final String dagger = "com.google.dagger:dagger:" + daggerVersion;
    public static final String daggerAndroid = "com.google.dagger:dagger-android:" + daggerVersion;
    public static final String daggerSupport = "com.google.dagger:dagger-android-support:" + daggerVersion;
    public static final String daggerCompiler = "com.google.dagger:dagger-compiler:" + daggerVersion;
    public static final String daggerProcessor = "com.google.dagger:dagger-android-processor:" + daggerVersion;
    public static final String glide = "com.github.bumptech.glide:glide:" + glideVersion;
    public static final String glideCompiler = "com.github.bumptech.glide:compiler:" + glideVersion;
    public static final String javaxInject = "javax.inject:javax.inject:" + javaxInjectVersion;
    public static final String moshi = "com.squareup.moshi:moshi-kotlin:" + moshiVersion;
    public static final String moshiAdapter = "com.squareup.moshi:moshi-adapters:" + moshiVersion;
    public static final String retrofit = "com.squareup.retrofit2:retrofit:" + retrofitVersion;
    public static final String retrofitConverter = "com.squareup.retrofit2:converter-moshi:" + retrofitVersion;
    public static final String okhttp = "com.squareup.okhttp3:okhttp:" + okhttpVersion;
    public static final String okhttpInterceptor = "com.squareup.okhttp3:logging-interceptor:" + okhttpVersion;
    public static final String timber = "com.jakewharton.timber:timber:" + timberVersion;

    // endregion
}
