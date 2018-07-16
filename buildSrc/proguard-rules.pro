-keepattributes Signature
-dontwarn javax.annotation.**

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
}

##---------------Begin: proguard configuration for Android  ----------
-keep class android.net.http.** { *; }
-dontwarn android.net.http.**
-dontnote android.net.http.*
-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**
-dontnote org.apache.http.**
-dontnote org.apache.commons.codec.**
##---------------End: proguard configuration for Android  ----------

##---------------Begin: proguard configuration for Kotlin  ----------
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
}
##---------------End: proguard configuration for Kotlin  ----------

##---------------Begin: proguard configuration for Glide  ----------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep class com.bumptech.glide.GeneratedAppGlideModuleImpl { *; }
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder
##---------------End: proguard configuration for Glide  ----------

##---------------Begin: proguard configuration for Moshi  ----------
-dontwarn okio.**
-keep @com.squareup.moshi.JsonQualifier interface *
-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
##---------------End: proguard configuration for Moshi  ----------

##---------------Begin: proguard configuration for Retrofit  ----------
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Exceptions
##---------------End: proguard configuration for Retrofit  ----------

##---------------Begin: proguard configuration for OkHttp  ----------
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-dontwarn org.codehaus.mojo.animal_sniffer.*
-dontwarn okhttp3.internal.platform.ConscryptPlatform
-dontwarn okio.**
##---------------End: proguard configuration for OkHttp  ----------
