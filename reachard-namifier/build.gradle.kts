plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.kuyucuburak.reachard.namifier"
    resourcePrefix = "reachard_namifier_"
}

dependencies {
    // Unit Test
    testImplementation(libs.unit.test.junit)
}
