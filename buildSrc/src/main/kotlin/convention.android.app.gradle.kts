import gradle.kotlin.dsl.accessors._76a779107637b25b34866585d88a55c4.ext

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jetbrains.kotlin.plugin.compose")
}

fun getExtraString(name: String) = rootProject.ext[name]?.toString() ?: ""

kotlin {
    jvmToolchain(19)
}

android {
    compileSdk = 35

    defaultConfig {
        targetSdk = 35
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("debug")

        buildConfigField(
            "String",
            "API_HOST",
            "\"${project.findProperty("API_HOST")}\""
        )

        buildConfigField(
            "String",
            "WS_API_HOST",
            "\"${project.findProperty("SOCKET_HOST")}\""
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
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
    api(LibraryDependencies.Msdk.core)

    //AndroidX
    implementation(LibraryDependencies.AndroidX.coreKtx)

    //Compose
    implementation(LibraryDependencies.Compose.ui)
    implementation(LibraryDependencies.Compose.material)
    implementation(LibraryDependencies.Compose.icons)
    implementation(LibraryDependencies.Compose.animationCore)
    implementation(LibraryDependencies.Compose.navigation)
    implementation(LibraryDependencies.Compose.activity)
    implementation(LibraryDependencies.Compose.lifecycleViewModel)

    implementation(LibraryDependencies.Coil.compose)


    //Serialization
    implementation(LibraryDependencies.KotlinX.serialization)

    //Accompanist
    implementation(LibraryDependencies.Accompanist.navigation)
    implementation(LibraryDependencies.Accompanist.permissions)

    //Google
    implementation(LibraryDependencies.Google.material)
    implementation(LibraryDependencies.Google.payButton)

    //Color picker
    implementation(LibraryDependencies.MsdkSample.colorPicker)

    //Testing
    androidTestImplementation("androidx.test:core:1.5.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${Versions.compose}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${Versions.compose}")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test.ext:junit:1.1.3")
    testImplementation("androidx.test.espresso:espresso-core:3.4.0")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.3")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        freeCompilerArgs.addAll("-opt-in=kotlin.RequiresOptIn", "-Xjvm-default=all")
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_19)
    }
}