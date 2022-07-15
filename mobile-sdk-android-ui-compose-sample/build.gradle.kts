plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}


android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32
        applicationId = "com.paymentpage.ui.test"

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

    flavorDimensions("brand")
    productFlavors {
        create("ecommpay") {
            dimension = "brand"
        }
    }
}


dependencies {
    //Projects
    implementation(project(":mobile-sdk-android-ui"))

    //AndroidX
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.lifecycleRuntimeKtx)

    //Compose
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.material)
    implementation(Dependencies.Compose.toolingPreview)
    testImplementation(Dependencies.Compose.junit4)
    debugImplementation(Dependencies.Compose.uiTooling)
    implementation(Dependencies.Compose.activity)

    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test.ext:junit:1.1.3")
    testImplementation("androidx.test.espresso:espresso-core:3.4.0")
}