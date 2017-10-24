# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\prabeer.kochar\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
-keepattributes *Annotation

-keep class retrofit.** { *; }
-keep interface retrofit.** { *;}

-dontwarn okio.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
# all classes in a package
-keep class com.media.ui.json.** { *; }
-keep class com.media.ui.ServerJobs.** { *; }
-keep interface com.media.ui.ServerJobs.requestAPI { *; }
# For OpenCSV
-dontwarn com.opencsv.**

-keep class org.apache.commons.** { *; }

-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}

-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**