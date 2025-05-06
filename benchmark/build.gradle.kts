@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinx.benchmark)
    alias(libs.plugins.allopen)
}

group = "io.github.kroune"
version = "unspecified"

repositories {
    mavenCentral()
}

android {
    namespace = "io.github.kroune.nine.mens.morris.library"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

kotlin {
    jvm()
    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs()
    js {
        nodejs()
    }
    sourceSets {
        commonMain.dependencies {
            implementation(project(":library"))
            implementation(libs.kotlinx.benchmark)
        }
    }
}

// build.gradle.kts
benchmark {
    targets {
        register("jvm")
        register("js")
        register("linuxX64")
    }
}

allOpen {
    annotation("org.openjdk.jmh.annotations.State")
}