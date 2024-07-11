plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
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

            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = (rootProject.extra["versions"] as Map<*, *>)["composeKotlinCompilerExtension"] as String
    }

    defaultConfig {
        applicationId = "com.kuyucuburak.reachard.sample"

        versionCode = rootProject.extra["versionCode"] as Int
        versionName = rootProject.extra["versionName"] as String
    }
}

dependencies {
    // reachard
    implementation(libs.reachard.di)
    implementation(libs.reachard.namifier)

    // androidx
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // jetpack compose
    implementation(platform(libs.bom.compose))
    implementation(libs.bom.compose.material3)
    implementation(libs.bom.compose.runtime.livedata)
    implementation(libs.bom.compose.ui)
    implementation(libs.bom.compose.ui.tooling)
}
