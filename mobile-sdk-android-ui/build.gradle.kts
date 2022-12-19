plugins {
    id("convention.android.library")
    id("org.cyclonedx.bom")
    id("convention.publication.ui")
}

group = System.getenv("SDK_GROUP") ?: Library.group

android {
    buildTypes {
        release {
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
}

dependencies {
    //msdkCore
    api(LibraryDependencies.Msdk.core)
}

tasks.cyclonedxBom {
    setIncludeConfigs(listOf("debugCompileClasspath"))
    setDestination(project.file("build/reports"))
    setOutputName("bom")
    setOutputFormat("json")
}