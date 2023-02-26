plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    buildToolsVersion = "33.0.0"
    compileSdk = 33
    namespace = "com.didomi.sampleappkotlin"

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        versionCode = 1
        versionName = "1.0"

        applicationId = "com.didomi.sampleappkotlin"
    }

    buildTypes {
        maybeCreate("release")
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    configurations {
        all {
            exclude(group = "androidx.lifecycle", module = "lifecycle-viewmodel-ktx")
        }
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation("io.didomi.sdk:android:1.75.0")

    implementation("androidx.core:core-ktx:1.9.0")

    implementation("com.google.android.gms:play-services-ads:21.4.0")
    implementation("com.google.android.material:material:1.7.0")
}
