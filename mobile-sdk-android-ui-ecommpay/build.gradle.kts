plugins {
    id("convention.android.library")
    id("convention.publication.ecommpay")
}

group = "com.ecommpay"

android {
    namespace = "com.ecommpay.msdk.ui"
    defaultConfig {
        vectorDrawables.useSupportLibrary = true
    }
}

dependencies {
    //Projects
    implementation(project(":mobile-sdk-android-ui"))
}