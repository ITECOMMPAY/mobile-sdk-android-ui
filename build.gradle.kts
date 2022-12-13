plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version(Versions.gradlePlugin).apply(false)
    kotlin("android").version(Versions.kotlin).apply(false)
    kotlin("plugin.serialization").version(Versions.kotlin).apply(false)

    id("org.cyclonedx.bom").version(Versions.cyclonedx).apply(false)
    //id("io.gitlab.arturbosch.detekt").version(Versions.detekt).apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

ext["signing.keyId"] = null
ext["signing.password"] = null
ext["signing.secretKeyRingFile"] = null
ext["ossrhUsername"] = null
ext["ossrhPassword"] = null
ext["sonatypeUrl"] = null

// Grabbing secrets from local.properties file or from environment variables, which could be used on CI
val secretPropsFile = project.rootProject.file("local.properties")
if (secretPropsFile.exists()) {
    secretPropsFile.reader().use {
        java.util.Properties().apply {
            load(it)
        }
    }.onEach { (name, value) ->
        extra[name.toString()] = value
    }
} else {
    ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
    ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
    ext["signing.secretKeyRingFile"] = System.getenv("SIGNING_SECRET_KEY_RING_FILE")
    ext["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
    ext["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")
    ext["sonatypeUrl"] = System.getenv("SONATYPE_URL")
}