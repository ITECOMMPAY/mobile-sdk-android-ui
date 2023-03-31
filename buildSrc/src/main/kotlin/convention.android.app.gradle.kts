plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("org.jetbrains.kotlin.plugin.serialization")
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("debug")
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

    flavorDimensions.add("mode")
    productFlavors {
        create("nl3") {
            dimension = "mode"
            buildConfigField(
                "String",
                "API_HOST",
                "\"pp-sdk.westresscode.net\""
            )

            buildConfigField(
                "String",
                "WS_API_HOST",
                "\"paymentpage.westresscode.net\""
            )
        }

        create("prod") {
            dimension = "mode"
            buildConfigField(
                "String",
                "API_HOST",
                "\"sdk.ecommpay.com\""
            )

            buildConfigField(
                "String",
                "WS_API_HOST",
                "\"paymentpage.ecommpay.com\""
            )
        }
    }

}

dependencies {
    //mSDK Core
    implementation(LibraryDependencies.Msdk.core)
    //AndroidX
    implementation(LibraryDependencies.AndroidX.coreKtx)

    //Compose
    implementation(LibraryDependencies.Compose.ui)
    implementation(LibraryDependencies.Compose.material)
    implementation(LibraryDependencies.Compose.activity)

    //Serialization
    implementation(LibraryDependencies.KotlinX.serialization)

    //Google
    implementation(LibraryDependencies.Google.material)

    //Color picker
    implementation(LibraryDependencies.MsdkSample.colorPicker)

    //Testing
    androidTestImplementation("androidx.test:core:1.5.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${Versions.compose}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${Versions.compose}")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test.ext:junit:1.1.3")
    testImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
        freeCompilerArgs += "-Xjvm-default=all"
    }
}