import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.api.AndroidBasePlugin

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

subprojects {
    plugins.withType<AndroidBasePlugin> {
        extensions.configure<CommonExtension> {
            compileSdk = libs.versions.sdk.compile.get().toInt()

            compileOptions.apply {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            defaultConfig.apply {
                minSdk = libs.versions.sdk.min.get().toInt()
            }
        }
    }

    plugins.withId("com.android.application") {
        extensions.configure<ApplicationExtension> {
            defaultConfig {
                targetSdk = libs.versions.sdk.target.get().toInt()
            }
        }
    }

    plugins.withId("com.android.library") {
        apply(plugin = "maven-publish")

        extensions.configure<LibraryExtension> {
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
                    artifactId = project.name

                    // The "release" software component is only registered once AGP has evaluated the project.
                    afterEvaluate {
                        from(components["release"])
                    }
                }
            }
        }
    }
}
