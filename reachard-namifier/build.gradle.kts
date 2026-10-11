plugins {
    alias(libs.plugins.android.library)
    id("maven-publish")
}

android {
    namespace = "com.kuyucuburak.reachard.namifier"
    resourcePrefix = "reachard_namifier_"
}

dependencies {
    // Unit Test
    testImplementation(libs.unit.test.junit)
}
