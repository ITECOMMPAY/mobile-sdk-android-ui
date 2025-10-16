import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("convention.android.library")
    id("com.vanniktech.maven.publish")
    id("signing")
}

group = Library.group
version = Library.version

mavenPublishing {
    coordinates(
        Library.group,
        Library.artifactId,
        Library.version
    )

    pom {
        name.set("mSDK UI Module")
        description.set("SDK for Android is a software development kit for fast integration of payment solutions right in your mobile application for Android.")
        url.set("https://github.com/ITMGLWALLET/mobile-sdk-android-ui")
        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set("<id>")
                name.set("<name>")
                url.set("<url>")
            }
        }
        scm {
            url.set("https://github.com/ITMGLWALLET/mobile-sdk-android-ui/")
        }
    }

    configure(
        AndroidSingleVariantLibrary(
            variant = "release",
            sourcesJar = true,
            publishJavadocJar = true,
        )
    )

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}

android {
    namespace = "com.mglwallet.msdk.ui"
    defaultConfig {
        vectorDrawables.useSupportLibrary = true
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(project(":mobile-sdk-android-ui"))
}