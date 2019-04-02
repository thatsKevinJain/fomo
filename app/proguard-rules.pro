## ButterKnife 8 ##
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }

# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinding.
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }

## CardView Android ##
-keep class android.support.v7.widget.RoundRectDrawable { *; }

# This is a configuration file for ProGuard. ##
-dontusemixedcaseclassnames

## Optimizations in APK File ##
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-repackageclasses ''
-dontpreverify

-ignorewarnings
-keep class * {
    public private *;
}