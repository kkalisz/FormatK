import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.detekt)
}

group = rootProject.group
version = rootProject.version

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
    watchosX64()
    tvosX64()
    tvosArm64()
    tvosSimulatorArm64()
    macosX64()
    macosArm64()

    js(IR) {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    applyDefaultHierarchyTemplate {
        common {
            group("commonJvm") {
                withJvm()
                withAndroidTarget()
            }
            group("commonJs") {
                withJs()
                withWasmJs()
            }
        }
    }

    sourceSets {
        @Suppress("UnusedPrivateMember")
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.datetime)
                // put your multiplatform dependencies here
            }
        }

        @Suppress("UnusedPrivateMember")
        val commonJsMain by getting {
            dependencies {
                implementation(npm("luxon", "3.4.3"))
            }
        }

        @Suppress("UnusedPrivateMember")
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }

    targets.configureEach {
        compilations.configureEach {
            compileTaskProvider.get().compilerOptions {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }
}

android {
    namespace = "com.bngdev.formatk.number"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, true)

    signAllPublications()

    coordinates(group.toString(), "formatk", version.toString())

    pom {
        name = "formatk-number"
        description = "Simple kotlin multiplatform library for formating number"
        inceptionYear = "2025"
        url = "https://github.com/kkalisz/FormatK"
        licenses {
            license {
                name.set("Apache License 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        scm {
            url.set("https://github.com/kkalisz/FormatK")
            connection.set("scm:git:git://github.com/kkalisz/FormatK.git")
        }

        developers {
            developer {
                id.set("kkalisz")
                name.set("Kamil Kalisz (kkalisz)")
                url.set("https://github.com/kkalisz")
            }
        }
    }
}
