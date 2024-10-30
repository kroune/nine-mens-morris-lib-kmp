import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "io.github.kroune"
version = "1.0.0"

kotlin {
    jvm()
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
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

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "io.github.kroune.nine.mens.morris.library"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(group.toString(), "nineMensMorris", version.toString())

    pom {
        name = "Nine men's morris library"
        description = "library for game \"nine mens morris\""
        inceptionYear = "2024"
        url = "https://github.com/kroune/nine-mens-morris-lib-kmp/"
        licenses {
            license {
                name = "GNU"
                url = "https://www.gnu.org/licenses/gpl-3.0-standalone.html"
                distribution = "https://www.gnu.org/licenses/gpl-3.0-standalone.html"
            }
        }
        developers {
            developer {
                id = "kroune"
                name = "kroune"
                url = "https://github.com/kroune"
            }
        }
        scm {
            url = "https://github.com/kroune/nine-mens-morris-lib-kmp/"
            connection = "scm:git:git://github.com/kroune/nine-mens-morris-lib-kmp.git"
            developerConnection = "scm:git:ssh://git@github.com/kroune/nine-mens-morris-lib-kmp.git"
        }
    }
}
