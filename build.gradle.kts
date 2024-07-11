buildscript {
    val buildNumber = System.getenv("BUILD_NUMBER")?.toInt() ?: 1

    extra.apply {
        set("versionMajor", 1)
        set("versionMinor", 0)
        set("versionPatch", 0)

        set("versionBuild", buildNumber)
        set("versionCode", (extra["versionMajor"] as Int) * 10000000 + (extra["versionMinor"] as Int) * 100000 + (extra["versionPatch"] as Int) * 1000 + (extra["versionBuild"] as Int))
        set("versionName", "${extra["versionMajor"]}.${extra["versionMinor"]}.${extra["versionPatch"]}")

        set("minSdkVersion", 21)
        set("targetSdkVersion", 34)
        set("compileSdkVersion", 34)

        set(
            "versions",
            mapOf(
                "composeKotlinCompilerExtension" to "1.5.14"
            )
        )
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
}

subprojects {
    afterEvaluate {
        if (plugins.hasPlugin("com.android.library") || plugins.hasPlugin("com.android.application")) {
            extensions.configure<com.android.build.gradle.BaseExtension> {
                compileSdkVersion(rootProject.extra["compileSdkVersion"] as Int)

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }

                defaultConfig {
                    minSdk = (rootProject.extra["minSdkVersion"] as Int)
                    targetSdk = (rootProject.extra["targetSdkVersion"] as Int)
                }
            }
        }

        if (plugins.hasPlugin("com.android.library")) {
            extensions.configure<com.android.build.gradle.LibraryExtension> {
                buildTypes.configureEach {
                    consumerProguardFiles("consumer-rules.pro")
                }

                defaultConfig {
                    consumerProguardFiles("consumer-proguard-rules.pro")
                }

                publishing {
                    singleVariant("release") {
                        withSourcesJar()
                        withJavadocJar()
                    }
                }
            }

            extensions.configure<PublishingExtension> {
                publications {
                    create<MavenPublication>("maven") {
                        artifactId = name

                        afterEvaluate {
                            from(components.getByName("release"))
                        }
                    }
                }
            }
        }
    }
}
