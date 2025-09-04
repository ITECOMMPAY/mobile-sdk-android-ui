plugins {
    id("convention.android.integration.example")
}

val projectId = "123"
val projectSecretKey = "123"
val gPayMerchantId = "BCR2DN6TZ75OBLTH"

android {
    namespace = "com.ecommpay.msdk.ui.integration.example"
    defaultConfig {
        applicationId = "com.ecommpay.msdk.ui.integration.example"
        versionName = System.getenv("SDK_VERSION_NAME") ?: Library.version
        versionCode = System.getenv("SDK_VERSION_CODE")?.toInt() ?: 1
    }

    buildTypes {
        buildTypes {
            getByName("debug") {
                buildConfigField(
                    "String",
                    "PROJECT_SECRET_KEY",
                    "\"" + projectSecretKey + "\""
                )

                buildConfigField(
                    "int",
                    "PROJECT_ID",
                    projectId
                )

                buildConfigField(
                    "String",
                    "GPAY_MERCHANT_ID",
                    "\"" + gPayMerchantId + "\""
                )
            }

            getByName("release") {
                buildConfigField(
                    "String",
                    "PROJECT_SECRET_KEY",
                    "\"" + projectSecretKey + "\""
                )

                buildConfigField(
                    "int",
                    "PROJECT_ID",
                    projectId
                )

                buildConfigField(
                    "String",
                    "GPAY_MERCHANT_ID",
                    "\"" + gPayMerchantId + "\""
                )
            }
        }
    }
}
dependencies {
    coreLibraryDesugaring(libs.tools.desugaring)
    implementation(project(":mobile-sdk-android-ui-ecommpay"))
}