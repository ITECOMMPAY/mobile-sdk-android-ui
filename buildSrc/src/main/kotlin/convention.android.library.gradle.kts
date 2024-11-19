plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id ("org.jetbrains.kotlin.plugin.serialization")
}

version = System.getenv("SDK_VERSION_NAME") ?: Library.version

android {
    compileSdk = 34

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField(
            "String",
            "SDK_VERSION_NAME",
            "\"$version\""
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
        debug {
            buildConfigField(
                "Boolean",
                "IS_TIME_TRAVEL",
                "true"
            )
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
                System.getenv("SDK_API_HOST") ?: "\"sdk.ecommpay.com\""
            )

            buildConfigField(
                "String",
                "WS_API_HOST",
                System.getenv("SDK_WS_API_HOST") ?: "\"paymentpage.ecommpay.com\""
            )
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    lint {
        quiet = true
        abortOnError = false
        ignoreWarnings = true
        checkDependencies = true

        enable.add("RtlHardcoded")
        enable.add("RtlCompat")
        enable.add("RtlEnabled")

        disable.add("TypographyFractions")
        disable.add("TypographyQuotes")
    }
}

dependencies {
    api(LibraryDependencies.Msdk.core)
    //Serialization
    implementation(LibraryDependencies.KotlinX.serialization)
    //CardIO (card scanning)
    implementation(LibraryDependencies.CardIO.cardIO)
    //AndroidX
    implementation(LibraryDependencies.AndroidX.appCompat)
    //Coil
    implementation(LibraryDependencies.Coil.compose)
    //Google
    implementation(LibraryDependencies.Google.wallet)
    //Compose
    implementation(LibraryDependencies.Compose.ui)
    implementation(LibraryDependencies.Compose.material)
    implementation(LibraryDependencies.Compose.icons)
    implementation(LibraryDependencies.Compose.animationCore)
    implementation(LibraryDependencies.Compose.toolingPreview)
    debugImplementation(LibraryDependencies.Compose.uiTooling)
    implementation(LibraryDependencies.Compose.navigation)
    implementation(LibraryDependencies.Compose.activity)
    //Accompanist
    implementation(LibraryDependencies.Accompanist.navigation)
    implementation(LibraryDependencies.Accompanist.permissions)
    //Testing
    testImplementation(LibraryDependencies.Compose.junit4)
    testImplementation("io.mockk:mockk:1.13.3")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test.ext:junit:1.2.1")
    testImplementation("androidx.test.espresso:espresso-core:3.6.1")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.3")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
        freeCompilerArgs += "-Xjvm-default=all"
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}