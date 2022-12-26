plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

val projectId = "123"
val projectSecretKey = "123"
val gPayMerchantId = "BCR2DN6TZ75OBLTH"

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "com.ecommpay.msdk.ui.example"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("debug") {
            buildConfigField(
                "String",
                "PROJECT_SECRET_KEY",
                "\"" + projectSecretKey + "\""
            )

            buildConfigField(
                "int",
                "PROJECT_ID",
                projectId
            )

            buildConfigField(
                "String",
                "GPAY_MERCHANT_ID",
                "\"" + gPayMerchantId + "\""
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
        kotlinCompilerExtensionVersion = "1.1.1"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("com.ecommpay:msdk-ui:2.1.1")

    //Compose
    implementation("androidx.compose.ui:ui:1.3.2")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("com.google.android.material:material:1.7.0")
    //Android
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity-ktx:1.6.1")
    implementation("androidx.core:core-ktx:1.9.0")
    //Additional
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
}
