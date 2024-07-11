plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.kuyucuburak.reachard.di"
    resourcePrefix = "reachard_di_"
}

dependencies {
    // unit test
    testImplementation(libs.tp.junit)
}
