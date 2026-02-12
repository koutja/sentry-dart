pluginManagement {
    val kotlinAndroidVersion = System.getenv("KOTLIN_ANDROID_PLUGIN_VERSION") ?: "2.1.20"

    plugins {
        id("org.jetbrains.kotlin.android") version kotlinAndroidVersion
        id("com.android.application") version "8.13.0"
        id("io.sentry.android.gradle") version "4.14.1"
    }

    val flutterSdkPath = run {
      val properties = java.util.Properties()
      file("local.properties").inputStream().use { properties.load(it) }
      val flutterSdkPath = properties.getProperty("flutter.sdk")
      require(flutterSdkPath != null) { "flutter.sdk not set in local.properties" }
      flutterSdkPath
    }

    // Разрешение символических ссылок и получение абсолютного пути помогает гредлу открыть каталог во всех случаях
    includeBuild(file("$flutterSdkPath/packages/flutter_tools/gradle").toPath().toRealPath().toAbsolutePath().toString())

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("dev.flutter.flutter-plugin-loader") version "1.0.0"
    id("com.android.application") apply false
    id("org.jetbrains.kotlin.android") apply false
    id("io.sentry.android.gradle") apply false
}

include(":app")
