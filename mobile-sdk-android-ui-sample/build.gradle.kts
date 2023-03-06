plugins {
    id("convention.android.app")
}

android {

    defaultConfig {
        applicationId = "com.ecommpay.ui.msdk.sample"
        versionName = System.getenv("SDK_VERSION_NAME") ?: Library.version
        versionCode = System.getenv("SDK_VERSION_CODE")?.toInt() ?: 1
    }
}

dependencies {
    implementation(project(":mobile-sdk-android-ui-ecommpay"))
}
