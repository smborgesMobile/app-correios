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

-keep class your.package.name.R$* { *; }

# Keep Kotlin Analysis API
-keep class org.jetbrains.kotlin.analysis.api.** { *; }
-keep class org.jetbrains.kotlin.** { *; }
-keepnames class org.jetbrains.kotlin.** { *; }

# Keep all Kotlin reflection metadata
-keepattributes *Annotation*
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# Keep all serializable classes
-keep class com.sborges.price.data.entities.** { *; }

# Retain all serializable classes and their generated serializers
-keepnames class **$$serializer
-keepnames class **$Companion

# Retain serialization annotations
-keepclassmembers class ** {
    @kotlinx.serialization.* *;
}

# Retain runtime annotations and metadata used by Kotlinx Serialization
-keepattributes RuntimeVisibleAnnotations
-keepattributes Annotation

# Prevent obfuscation of enums (if applicable)
-keepclassmembers enum * { *; }

# Firebase Remote Config
-dontwarn com.google.firebase.**
-keep class com.google.firebase.** {*;}
-keepclassmembers class com.google.firebase.** { *; }