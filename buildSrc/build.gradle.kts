plugins {
    `kotlin-dsl`
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}