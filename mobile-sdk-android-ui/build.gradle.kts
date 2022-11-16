plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("io.gitlab.arturbosch.detekt")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32
        version = System.getenv("SDK_VERSION_NAME") ?: Library.version

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
            buildConfigField(
                "Boolean",
                "IS_TIME_TRAVEL",
                "false"
            )
            buildConfigField(
                "String",
                "SDK_VERSION_NAME",
                "\"$version\""
            )
        }
        debug {
            buildConfigField(
                "Boolean",
                "IS_TIME_TRAVEL",
                "true"
            )
            buildConfigField(
                "String",
                "SDK_VERSION_NAME",
                "\"$version\""
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
    api(Dependencies.Msdk.core)
    //AndroidX
    implementation(Dependencies.AndroidX.appCompat)
    //Coil
    implementation(Dependencies.Coil.compose)
    //Google
    implementation(Dependencies.Google.wallet)
    //Compose
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.material)
    implementation(Dependencies.Compose.animationCore)
    implementation(Dependencies.Compose.toolingPreview)
    testImplementation(Dependencies.Compose.junit4)
    debugImplementation(Dependencies.Compose.uiTooling)
    implementation(Dependencies.Compose.navigation)
    implementation(Dependencies.Compose.activity)
    //Accompanist
    implementation(Dependencies.Accompanist.navigation)

    //Testing
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


val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}