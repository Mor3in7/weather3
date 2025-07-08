plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt) // اطمینان از نام دقیق در libs.versions.toml
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

        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    // اگر از BOM استفاده می‌کنی، این خط را می‌تونی حذف کنی یا نسخه را بروز کن
    //composeOptions {
    //    kotlinCompilerExtensionVersion = "1.5.3"
    //}
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.bundles.androidX)

    implementation(platform(libs.compose.bom))
    implementation(libs.transport.runtime)
    debugImplementation(libs.compose.tooling)
    implementation(libs.bundles.ui)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.androidx.core)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)  // kapt نه ksp برای Room

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.androidx.hilt.compiler)
}
