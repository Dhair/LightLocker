# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\i\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
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

#Common(Retrofit、Okhttp)
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*
-dontwarn okio.**

#Butterknife start
-keep public class * implements butterknife.internal.ViewBinder { public <init>(); }
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }
#Butterknife end

#Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

#OkHttp
-keep class okhttp3.** { *; }
-keep interface okhttp3.* { *; }
-dontwarn okhttp3.*

#RxJava
-dontwarn rx.**
-keep class rx.** { * ; }

#LeakCanary
-keep class org.eclipse.mat.** { *; }
-keep class com.squareup.leakcanary.** { *; }
