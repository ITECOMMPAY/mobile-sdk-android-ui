plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    //id("io.gitlab.arturbosch.detekt")
    id("org.cyclonedx.bom")
    id("maven-publish")
    id("signing")
}

group = System.getenv("SDK_GROUP") ?: Library.group
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
            buildConfigField(
                "Boolean",
                "IS_TIME_TRAVEL",
                "false"
            )
        }
        debug {
            buildConfigField(
                "Boolean",
                "IS_TIME_TRAVEL",
                "true"
            )
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

tasks.cyclonedxBom {
    setIncludeConfigs(listOf("debugCompileClasspath"))
    setDestination(project.file("build/reports"))
    setOutputName("bom")
    setOutputFormat("json")
}

fun getExtraString(name: String) = rootProject.ext[name]?.toString() ?: ""

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

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
                from(components["release"])

                artifactId = "${Library.artifactId}-common"

                // Stub javadoc.jar artifact
                artifact(javadocJar.get())

                // Provide artifacts information requited by Maven Central
                pom {
                    name.set("mSDK UI Module")
                    description.set(getExtraString("projectDescription"))
                    url.set(getExtraString("projectUrl"))
                    licenses {
                        license {
                            name.set("MIT")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    developers {
                        developer {
                            id.set(getExtraString("developerId"))
                            name.set(getExtraString("developerName"))
                            email.set(getExtraString("developerEmail"))
                        }
                    }
                    scm {
                        url.set("https://github.com/ITECOMMPAY/paymentpage-sdk-android-core")
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