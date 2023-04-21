@file:Suppress("unused")

object Library {
    const val artifactId = "msdk-ui"
    const val version = "3.3.1"
    const val group = "com.ecommpay"
}

object Versions {
    const val compose = "1.4.2"
    const val detekt = "1.20.0"
    const val msdkCore = "0.9.0"
    const val cyclonedx = "1.7.2"
}

object LibraryDependencies {
    object CardIO {
        const val cardIO= "io.card:android-sdk:5.5.1"
    }

    object Compose {
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val animationCore = "androidx.compose.animation:animation:${Versions.compose}"
        const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
        const val runtimeLiveData = "androidx.compose.runtime:runtime:${Versions.compose}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val junit4 = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
        const val navigation = "androidx.navigation:navigation-compose:2.5.3"
        const val activity = "androidx.activity:activity-compose:1.7.0"
    }

    object Accompanist {
        const val navigation =
            "com.google.accompanist:accompanist-navigation-animation:0.31.0-alpha"
        const val permissions = "com.google.accompanist:accompanist-permissions:0.25.1"
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.7.0-alpha02"
        const val coreKtx = "androidx.core:core-ktx:1.10.0"
        const val activityKtx = "androidx.activity:activity-ktx:1.7.1"
    }

    object KotlinX {
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0"
    }

    object Coil {
        const val compose = "io.coil-kt:coil-compose:2.3.0"
    }

    object Google {
        const val wallet = "com.google.android.gms:play-services-wallet:19.1.0"
        const val material = "com.google.android.material:material:1.6.1"
    }

    object Msdk {
        const val core = "com.ecommpay:msdk-core-android:${Versions.msdkCore}"
    }

    object MsdkSample {
        const val colorPicker = "io.github.vanpra.compose-material-dialogs:color:0.8.1-rc"
    }
}