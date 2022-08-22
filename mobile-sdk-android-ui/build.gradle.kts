plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("io.gitlab.arturbosch.detekt")
    id("maven-publish")
    id("signing")
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
        flavorDimensions("brand")
        productFlavors {
            create("ecommpay") {
                dimension = "brand"

                buildConfigField(
                    "String",
                    "API_HOST",
                    "\"sdk.ecommpay.com\""
                )

                buildConfigField(
                    "String",
                    "WS_API_HOST",
                    "\"paymentpage.ecommpay.com\""
                )
            }
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
    implementation(Dependencies.AndroidX.lifecycleRuntimeKtx)
    implementation(Dependencies.AndroidX.constraintLayout)
    debugImplementation(Dependencies.AndroidX.customView)
    debugImplementation(Dependencies.AndroidX.customViewPoolingContainer)
    implementation(Dependencies.AndroidX.coreKtx)
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

fun getExtraString(name: String) = rootProject.ext[name]?.toString()

afterEvaluate {

    publishing {
        // Configure maven central repository
        repositories {
            maven {
                name = "sonatype"
                setUrl("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = getExtraString("ossrhUsername")
                    password = getExtraString("ossrhPassword")
                }
            }
        }

        publications.create<MavenPublication>("ecommpayRelease") {
            from(project.components["ecommpayRelease"])
            groupId = Library.ecommpayGroup
            artifactId = Library.artifactId
            version = Library.version

            // Stub javadoc.jar artifact
            artifact(javadocJar.get())

            // Provide artifacts information requited by Maven Central
            pom {
                name.set("mSDK UI Module")
                description.set("SDK for Android is a software development kit for fast integration of the ECommPay payment solutions right in your mobile application for Android.")
                url.set("https://github.com/ITECOMMPAY/")

                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("ecpit")
                        name.set("Alexey Khrameev")
                        email.set("a.khrameev@it.ecommpay.com")
                    }
                    developer {
                        id.set("ecpit")
                        name.set("Roman Karpilenko")
                        email.set("r.karpilenko@it.ecommpay.com")
                    }
                }
                scm {
                    url.set("https://github.com/ITECOMMPAY/paymentpage-sdk-android-core")
                }

            }
        }
    }

    // Signing artifacts. Signing.* extra properties values will be used
    signing {
        sign(publishing.publications)
    }
}
