# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keepclassmembernames interface * {
    @retrofit2.http.* <methods>;
}

-keep class com.google.gson.** { *; }
-keep class br.com.smdevelopment.rastreamentocorreios.entities.** { *; }
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class com.google.gson.internal.** { *; }
-keep class br.com.smdevelopment.rastreamentocorreios.** { *; }
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
-keep class com.google.gson.** { *; }
-keep class com.squareup.moshi.** { *; }
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn retrofit2.**
-dontwarn okhttp3.**
-dontwarn com.google.gson.**
-dontwarn com.squareup.moshi.**
-keep interface br.com.smdevelopment.rastreamentocorreios.api.** { *; }

# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
 -keep,allowobfuscation,allowshrinking interface retrofit2.Call
 -keep,allowobfuscation,allowshrinking class retrofit2.Response

 # With R8 full mode generic signatures are stripped for classes that are not
 # kept. Suspend functions are wrapped in continuations where the type argument
 # is used.
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation


# GSON Annotations
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}
