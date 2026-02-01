plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 36
    namespace = "com.didomi.sampleappkotlin"

    defaultConfig {
        minSdk = 23
        targetSdk = 36

        versionCode = 1
        versionName = "1.0"

        applicationId = "com.didomi.sampleappkotlin"
    }

    buildTypes {
        maybeCreate("release")
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
}

dependencies {
    implementation("io.didomi.sdk:android:2.36.1")
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("com.google.android.gms:play-services-ads:24.8.0")
    implementation("com.google.android.material:material:1.13.0")
}
