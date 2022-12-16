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
ext["developerId"] = null
ext["developerName"] = null
ext["developerEmail"] = null
ext["projectDescription"] = null
ext["projectUrl"] = null
ext["repositoryUrl"] = null

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
    ext["developerId"] = System.getenv("DEVELOPER_ID")
    ext["developerName"] = System.getenv("DEVELOPER_NAME")
    ext["developerEmail"] = System.getenv("DEVELOPER_EMAIL")
    ext["projectDescription"] = System.getenv("PROJECT_DESCRIPTION")
    ext["projectUrl"] = System.getenv("PROJECT_URL")
    ext["repositoryUrl"] = System.getenv("REPOSITORY_URL")
}

subprojects {
    tasks.withType(Test::class.java) {
        testLogging {
            showCauses = false
            showExceptions = false
            showStackTraces = false
            showStandardStreams = false

            val ansiReset = "\u001B[0m"
            val ansiGreen = "\u001B[32m"
            val ansiRed = "\u001B[31m"
            val ansiYellow = "\u001B[33m"

            fun getColoredResultType(resultType: TestResult.ResultType): String {
                return when (resultType) {
                    TestResult.ResultType.SUCCESS -> "$ansiGreen $resultType $ansiReset"
                    TestResult.ResultType.FAILURE -> "$ansiRed $resultType $ansiReset"
                    TestResult.ResultType.SKIPPED -> "$ansiYellow $resultType $ansiReset"
                }
            }

            afterTest(
                KotlinClosure2({ desc: TestDescriptor, result: TestResult ->
                    println("${desc.displayName} = ${getColoredResultType(result.resultType)}")
                })
            )

            afterSuite(
                KotlinClosure2({ desc: TestDescriptor, result: TestResult ->
                    if (desc.parent == null) {
                        println("Result: ${result.resultType} (${result.testCount} tests, ${result.successfulTestCount} passed, ${result.failedTestCount} failed, ${result.skippedTestCount} skipped)")
                    }
                })
            )
        }
    }
}