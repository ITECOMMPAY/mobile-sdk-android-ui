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

        signingConfig = signingConfigs.getByName("debug")
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
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
        freeCompilerArgs += "-Xjvm-default=all"
    }
}