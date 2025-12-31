import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

subprojects {
    afterEvaluate {
        if (plugins.hasPlugin("com.android.application") || plugins.hasPlugin("com.android.library")) {
            extensions.configure<BaseExtension> {
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }

                defaultConfig {
                    minSdk = libs.versions.sdk.min.get().toInt()
                    targetSdk = libs.versions.sdk.target.get().toInt()
                }
            }
        }

        if (plugins.hasPlugin("com.android.application")) {
            extensions.configure<ApplicationExtension> {
                compileSdk = libs.versions.sdk.compile.get().toInt()
            }
        }

        if (plugins.hasPlugin("com.android.library")) {
            extensions.configure<LibraryExtension> {
                compileSdk = libs.versions.sdk.compile.get().toInt()

                buildTypes.configureEach {
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
                        artifactId = project.name

                        afterEvaluate {
                            from(components.getByName("release"))
                        }
                    }
                }
            }
        }
    }
}
