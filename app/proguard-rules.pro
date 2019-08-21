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
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** e(...);
    public static *** w(...);
}

# x5core
-dontwarn com.tencent.**
-keep class com.tencent.**{*;}
-keep interface com.tencent.**{*;}

##mvp arms
-dontwarn com.jess.arms.**
-keep class com.jess.arms.**{*;}

-dontwarn me.jessyan.**
-keep class me.jessyan.**{*;}

##项目中bean类
-keep class com.zky.tea_time.mvp.bean.**{*;}
-keep class com.zky.tea_time.mvp.model.**{*;}
-keep class com.zky.tea_time.mvp.contract.**{*;}
-keep class com.zky.tea_time.app.**{*;}

####retrofit-mock
-keep class retrofit2.** {*;}
-keep class com.chaoliu.mock.** {*;}

#umeng
-keep public class com.hexin.wealth.R$*{
    public static final int *;
}
#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}