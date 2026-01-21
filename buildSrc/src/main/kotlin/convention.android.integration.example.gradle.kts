plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    jvmToolchain(19)
}

android {
    compileSdk = 35

    defaultConfig {
        targetSdk = 35
        minSdk = 21
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

    buildFeatures {
        compose = true
        buildConfig = true
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
        isCoreLibraryDesugaringEnabled = true
    }

}

dependencies {
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
    implementation(LibraryDependencies.Google.payButton)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        freeCompilerArgs.addAll("-opt-in=kotlin.RequiresOptIn", "-Xjvm-default=all")
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_19)
    }
}