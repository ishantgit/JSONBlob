# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/ishant/Library/Android/sdk/tools/proguard/proguard-android.txt
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
-dontpreverify
-repackageclasses ''
-allowaccessmodification
-flattenpackagehierarchy 'me'

-optimizations !code/simplification/arithmetic

-keepattributes SourceFile, LineNumberTable

-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * implements android.os.Parcelable {
    static android.os.Parcelable$Creator CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}


# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.FieldNamingStrategy { *; }
-keep class com.google.common.** { *; }

-dontwarn com.google.common.**

-keep class com.example.ishant.jsonblob.models.enums.** { *; }

# Keep Retrofit
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.** *;
}
-keepclassmembers class * {
    @retrofit2.** *;
}

-keepattributes Exceptions

-dontwarn okhttp3.**
-keep class okhttp3.** { *;}
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**

-dontwarn java.lang.reflect.Method**
-dontwarn java.lang.invoke.MethodHandles**
-dontwarn java.lang.invoke.MethodHandle**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement**

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}