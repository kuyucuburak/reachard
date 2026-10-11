@file:Suppress("UnstableApiUsage")

rootProject.name = "reachard"

include(
    ":reachard-di",
    ":reachard-namifier",
    ":sample",
)

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS

    repositories {
        google()
        mavenCentral()

        maven(url = "https://jitpack.io")
    }
}
