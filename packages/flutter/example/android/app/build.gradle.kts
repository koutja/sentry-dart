import java.util.*

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dev.flutter.flutter-gradle-plugin")
}

val javaTargetVersion = JavaVersion.VERSION_11
android {
    namespace = "io.sentry.samples.flutter"
    compileSdk = 36
    ndkVersion = "28.2.13676358"
    compileOptions {
        sourceCompatibility = javaTargetVersion
        targetCompatibility = javaTargetVersion
    }
    kotlinOptions {
        jvmTarget = javaTargetVersion.toString()
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }

    defaultConfig {
        applicationId = "io.sentry.samples.flutter"
        minSdk = 24
        targetSdk = 36
        versionCode = flutter.versionCode
        versionName = flutter.versionName

        externalNativeBuild {
            cmake {
                arguments.addAll(
                    listOf(
                        "-DANDROID_STL=c++_static",
                        // https://developer.android.com/guide/practices/page-sizes#compile-r27
                        "-DANDROID_SUPPORT_FLEXIBLE_PAGE_SIZES=ON"
                    )
                )
            }
        }

        ndk {
            // Flutter does not currently support building for x86 Android (See Issue 9253).
            abiFilters.addAll(listOf("armeabi-v7a", "x86_64", "arm64-v8a"))
        }
    }

    ndkVersion = "27.2.12479018"

    externalNativeBuild {
        cmake {
            path = File("CMakeLists.txt")
        }
    }

    buildTypes {
        getByName("release") {
            // looks like Flutter requires minifyEnabled to be enabled or it throws
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    lint {
        // workaround if using AGP 4.0 on release mode
        // https://github.com/flutter/flutter/issues/58247
        checkReleaseBuilds = false
    }
}

flutter {
    source = "../.."
}

dependencies {
    implementation("androidx.annotation:annotation:1.1.0")
}
