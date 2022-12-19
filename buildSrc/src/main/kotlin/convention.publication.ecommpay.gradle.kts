plugins {
    id("maven-publish")
    id("signing")
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

fun getExtraString(name: String) = rootProject.ext[name]?.toString() ?: ""

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
