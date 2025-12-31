plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.kuyucuburak.reachard.sample"

    buildFeatures {
        compose = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    defaultConfig {
        applicationId = "com.kuyucuburak.reachard.sample"

        versionCode = 1
        versionName = "1.0.0"
    }
}

dependencies {
    // BOM Compose
    implementation(platform(libs.bom.compose))
    implementation(libs.bom.compose.material3)
    implementation(libs.bom.compose.runtime.livedata)
    implementation(libs.bom.compose.ui)
    implementation(libs.bom.compose.ui.tooling)

    // AndroidX
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Third Party
    implementation(libs.third.party.reachard.di)
    implementation(libs.third.party.reachard.namifier)
}
