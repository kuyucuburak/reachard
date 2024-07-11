plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.kuyucuburak.reachard.namifier"
    resourcePrefix = "reachard_namifier_"
}

dependencies {
    // unit test
    testImplementation(libs.tp.junit)
}
