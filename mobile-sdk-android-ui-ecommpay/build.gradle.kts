import com.vanniktech.maven.publish.AndroidSingleVariantLibrary

plugins {
    id("convention.android.library")
    id("com.vanniktech.maven.publish")
    id("signing")
}

fun getExtraString(name: String) = rootProject.ext[name]?.toString() ?: ""

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}


mavenPublishing {
    configure(
        AndroidSingleVariantLibrary(
            // the published variant
            variant = "prodRelease",
            // whether to publish a sources jar
            sourcesJar = true,
            // whether to publish a javadoc jar
            publishJavadocJar = true,
        )
    )
    coordinates(System.getenv("GROUP_ID") ?: Library.group, Library.artifactId, Library.version)

    pom {
        name.set("mSDK UI Module")
        description.set("SDK for Android is a software development kit for fast integration of payment solutions right in your mobile application for Android.")
        url.set("${System.getenv("GITHUB_URL") ?: ""}/mobile-sdk-android-ui")
        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set(System.getenv("DEVELOPER_ID"))
                name.set(System.getenv("DEVELOPER_NAME"))
                email.set(System.getenv("DEVELOPER_EMAIL"))
            }
        }
        scm {
            url.set("${System.getenv("GITHUB_URL") ?: ""}/mobile-sdk-android-ui")
        }

    }
}


group = System.getenv("GROUP_ID") ?: Library.group

android {
    namespace = "com.ecommpay.msdk.ui"
    defaultConfig {
        vectorDrawables.useSupportLibrary = true
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    //Projects
    implementation(project(":mobile-sdk-android-ui"))
}

signing {
    useGpgCmd()
}
