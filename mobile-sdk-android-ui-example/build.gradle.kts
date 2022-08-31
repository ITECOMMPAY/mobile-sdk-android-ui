import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val projectId: String = gradleLocalProperties(rootDir).getProperty("example.projectId")
val salt: String = gradleLocalProperties(rootDir).getProperty("example.salt")

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.paymentpage.ui.msdk.example"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField(
            "Integer",
            "EXAMPLE_PROJECT_ID",
            projectId
        )
        buildConfigField(
            "String",
            "EXAMPLE_SALT",
            "\"$salt\""
        )
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
        jvmTarget = "1.8"
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
    //Msdk-ui
    implementation("com.ecommpay:msdk-ui:2.0.0")
    //Compose
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.material)
    implementation(Dependencies.Compose.animationCore)
    implementation(Dependencies.Compose.toolingPreview)
    debugImplementation(Dependencies.Compose.uiTooling)
    implementation(Dependencies.Compose.navigation)
    implementation(Dependencies.Compose.activity)
    testImplementation(Dependencies.Compose.junit4)
    //Android
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}