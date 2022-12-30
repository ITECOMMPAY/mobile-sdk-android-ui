plugins {
    id("convention.android.library")
    id("convention.publication.ecommpay")
}

group = "com.ecommpay"

dependencies {
    //Projects
    implementation(project(":mobile-sdk-android-ui"))

    android {
        defaultConfig {
            vectorDrawables.useSupportLibrary = true
        }
    }
}