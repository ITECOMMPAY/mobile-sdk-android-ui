-keep, allowobfuscation, allowoptimization class org.kodein.type.TypeReference
-keep, allowobfuscation, allowoptimization class org.kodein.type.JVMAbstractTypeToken$Companion$WrappingTest

-keep, allowobfuscation, allowoptimization class * extends org.kodein.type.TypeReference
-keep, allowobfuscation, allowoptimization class * extends org.kodein.type.JVMAbstractTypeToken$Companion$WrappingTest

-keep class com.mglwallet.msdk.ui.** { *; }
-keepclassmembers class com.mglwallet.msdk.ui.** { *; }
-keep interface com.mglwallet.msdk.ui.**

-keep class com.paymentpage.msdk.core.** { *; }
-keepclassmembers class com.paymentpage.msdk.core.** { *; }
-keep interface com.paymentpage.msdk.core.**