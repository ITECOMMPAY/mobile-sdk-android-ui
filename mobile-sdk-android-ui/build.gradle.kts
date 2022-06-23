plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    kotlin("plugin.serialization")
    id("io.gitlab.arturbosch.detekt")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //msdkCore
    implementation("com.ecommpay:msdk-core:+")
    //AndroidX
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.lifecycleRuntimeKtx)
    implementation(Dependencies.AndroidX.constraintLayout)
    debugImplementation(Dependencies.AndroidX.customView)
    debugImplementation(Dependencies.AndroidX.customViewPoolingContainer)
    implementation(Dependencies.AndroidX.coreKtx)
    //Gson
    //implementation("com.google.code.gson:gson:2.9.0")
    //Coil
    implementation(Dependencies.Coil.compose)
    //Serialization
    implementation(Dependencies.Serialization.json)
    //Google
    implementation(Dependencies.Google.wallet)
    implementation(Dependencies.Google.material)
    //Compose
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.material)
    implementation(Dependencies.Compose.animationCore)
    implementation(Dependencies.Compose.toolingPreview)
    implementation(Dependencies.Compose.runtimeLiveData)
    testImplementation(Dependencies.Compose.junit4)
    debugImplementation(Dependencies.Compose.uiTooling)
    implementation(Dependencies.Compose.navigation)
    implementation(Dependencies.Compose.lifecycleViewModel)
    implementation(Dependencies.Compose.activity)
    //Accompanist
    implementation(Dependencies.Accompanist.navigation)

    //Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test.ext:junit:1.1.3")
    testImplementation("androidx.test.espresso:espresso-core:3.4.0")
}