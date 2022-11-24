buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.2")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        //classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt}")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}