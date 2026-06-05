plugins {
    id("convention.android.app")
    id("signing")
}

android {
    namespace = "ru.etoplatezhi.ui.msdk.sample"
    defaultConfig {
        applicationId = "ru.etoplatezhi.ui.msdk.sample"
        versionName = System.getenv("SDK_VERSION_NAME") ?: Library.version
        versionCode = System.getenv("SDK_VERSION_CODE")?.toInt() ?: 1
    }
    buildTypes {
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }
}

dependencies {
    implementation(project(":mobile-sdk-android-ui-etoplatezhi"))
    implementation(project(":mobile-sdk-android-ui"))
}
