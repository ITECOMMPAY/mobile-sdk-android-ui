plugins {
    id("maven-publish")
    id("signing")
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
