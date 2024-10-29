-keep, allowobfuscation, allowoptimization class org.kodein.type.TypeReference
-keep, allowobfuscation, allowoptimization class org.kodein.type.JVMAbstractTypeToken$Companion$WrappingTest

-keep, allowobfuscation, allowoptimization class * extends org.kodein.type.TypeReference
-keep, allowobfuscation, allowoptimization class * extends org.kodein.type.JVMAbstractTypeToken$Companion$WrappingTest

-keep class com.ecommpay.msdk.ui.** { *; }
-keepclassmembers class com.ecommpay.msdk.ui.** { *; }
-keep interface com.ecommpay.msdk.ui.**

-keep class com.paymentpage.msdk.core.** { *; }
-keepclassmembers class com.paymentpage.msdk.core.** { *; }
-keep interface com.paymentpage.msdk.core.**