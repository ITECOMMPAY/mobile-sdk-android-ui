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