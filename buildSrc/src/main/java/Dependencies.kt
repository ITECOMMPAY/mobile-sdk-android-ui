@file:Suppress("unused")

object Versions {
    const val gradlePlugin = "7.1.3"
    const val kotlin = "1.6.21"
    const val compose = "1.2.0-beta03"
    const val serialization = "1.3.3"
    const val detekt = "1.20.0"
    const val dagger = "2.38.1"
}

object Dependencies {
    object Compose {
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val animationCore = "androidx.compose.animation:animation-core:1.2.0-rc01"
        const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
        const val runtimeLiveData = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val junit4 = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
        const val navigation = "androidx.navigation:navigation-compose:2.4.2"
        const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1"
        const val activity = "androidx.activity:activity-compose:1.4.0"
    }

    object Accompanist {
        const val navigation =
            "com.google.accompanist:accompanist-navigation-animation:0.24.7-alpha"
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.4.2"
        const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.1"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val customView = "androidx.customview:customview:1.2.0-alpha01"
        const val customViewPoolingContainer =
            "androidx.customview:customview-poolingcontainer:1.0.0-rc01"
        const val coreKtx = "androidx.core:core-ktx:1.8.0"
        const val activityKtx = "androidx.activity:activity-ktx:1.6.0-alpha05"
    }

    object Coil {
        const val compose = "io.coil-kt:coil-compose:2.0.0-rc03"
    }

    object Serialization {
        const val json =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}"
    }

    object Google {
        const val wallet = "com.google.android.gms:play-services-wallet:19.1.0"
        const val material = "com.google.android.material:material:1.6.1"
    }

    object Dagger {
        const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
        const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.dagger}"
        const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.dagger}"
    }

}