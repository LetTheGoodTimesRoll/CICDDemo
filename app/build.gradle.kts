plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.cicddemo"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.cicddemo"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            // CI/CD 优先使用环境变量（GitHub Secrets 注入）；本地使用工程内 keystore
            val keystorePath = System.getenv("KEYSTORE_PATH")
            storeFile = if (keystorePath != null) file(keystorePath) else file("platform.keystore")
            storePassword = System.getenv("KEYSTORE_PASSWORD") ?: "android"
            keyAlias = System.getenv("KEY_ALIAS") ?: "1"
            keyPassword = System.getenv("KEY_PASSWORD") ?: "android"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}