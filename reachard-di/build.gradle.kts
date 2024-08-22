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
    // Unit Test
    testImplementation(libs.tp.junit)
}
