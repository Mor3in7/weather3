plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "androidlead.weatherappui"
    compileSdk = 35

    defaultConfig {
        applicationId = "androidlead.weatherappui"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        vectorDrawables { useSupportLibrary = true }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }

    buildFeatures { compose = true }

    packaging {
        resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" }
    }
}

dependencies {
    // Use the corrected bundle name: 'core'
    implementation(libs.bundles.core)

    // Compose
    implementation(platform(libs.compose.bom))
    // Use the corrected bundle name: 'compose'
    implementation(libs.bundles.compose)
    debugImplementation(libs.compose.tooling)
    implementation(libs.androidx.navigation.compose)

    // DataStore + Coroutines
    implementation(libs.androidx.datastore.preferences)
    // Use the coroutines bundle for cleaner code
    implementation(libs.bundles.coroutines)

    // Networking (Serialization)
    implementation(libs.retrofit)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    // Room (with KSP)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Hilt - The Hilt compiler was missing!
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler) // <-- CRITICAL: This line was missing
    implementation(libs.androidx.hilt.navigation.compose)

    // The 'transport' and 'benchmark' libraries were removed from the TOML file.
    // They must be removed from here as well.
}