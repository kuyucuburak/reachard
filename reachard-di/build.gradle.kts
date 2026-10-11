plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.kuyucuburak.reachard.di"
    resourcePrefix = "reachard_di_"
}

dependencies {
    // Unit Test
    testImplementation(libs.unit.test.junit)
}
