plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("maven-publish")
    id("signing")
}

group = "com.ecommpay"
version = System.getenv("SDK_VERSION_NAME") ?: Library.version

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33
        version = System.getenv("SDK_VERSION_NAME") ?: Library.version

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField(
            "String",
            "SDK_VERSION_NAME",
            "\"$version\""
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
        debug {

        }
    }

    flavorDimensions.add("mode")
    productFlavors {
        create("nl3") {
            dimension = "mode"
        }

        create("prod") {
            dimension = "mode"
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

    //Projects
    implementation(project(":mobile-sdk-android-ui"))

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
    testImplementation("androidx.test.ext:junit:1.1.4")
    testImplementation("androidx.test.espresso:espresso-core:3.5.0")
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
                setUrl(getExtraString("sonatypeUrl"))
                credentials {
                    username = getExtraString("ossrhUsername")
                    password = getExtraString("ossrhPassword")
                }
            }
        }

        publications {
            register("release", MavenPublication::class) {
                from(components["prodRelease"])

                artifactId = Library.artifactId

                // Stub javadoc.jar artifact
                artifact(javadocJar.get())

                // Provide artifacts information requited by Maven Central
                pom {
                    name.set("mSDK UI Module")
                    description.set("SDK for Android is a software development kit for fast integration of the ECommPay payment solutions right in your mobile application for Android.")
                    url.set("https://github.com/ITECOMMPAY/mobile-sdk-android-ui")

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
                        url.set(getExtraString("repositoryUrl"))
                    }

                }
            }
        }

    }

    // Signing artifacts. Signing.* extra properties values will be used
    signing {
        sign(publishing.publications)
    }
}
