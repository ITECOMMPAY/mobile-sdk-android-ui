plugins {
    id("convention.android.app")
    id("signing")
}

android {
    namespace = "com.mglwallet.ui.msdk.sample"
    defaultConfig {
        applicationId = "com.mglwallet.ui.msdk.sample"
        versionName = System.getenv("SDK_VERSION_NAME") ?: Library.version
        versionCode = System.getenv("SDK_VERSION_CODE")?.toInt() ?: 1
    }
}

dependencies {
    implementation(project(":mobile-sdk-android-ui-mglwallet"))
    implementation(project(":mobile-sdk-android-ui"))
}
