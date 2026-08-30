plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "mobile.solareye.cookflow"
    compileSdk = 30

    defaultConfig {
        applicationId = "mobile.solareye.cookflow"
        minSdk = 26
        targetSdk = 30
        versionCode = 1
        versionName = "0.0.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("mocked") {
            initWith(getByName("debug"))
            applicationIdSuffix = ".mocked"
            versionNameSuffix = "-mocked"
            matchingFallbacks += listOf("debug")
        }
    }

    sourceSets {
        getByName("debug").kotlin.directories.add("src/real/kotlin")
        getByName("release").kotlin.directories.add("src/real/kotlin")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.bundles.app)

    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.android.test)
}
