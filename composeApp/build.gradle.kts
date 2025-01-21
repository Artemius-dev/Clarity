import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.googleServices)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    if (providers.gradleProperty("include_ios").get().toBoolean()) {
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64()
        ).forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = "ComposeApp"
                isStatic = true
            }
        }
    }


    if (providers.gradleProperty("include_desktop").get().toBoolean()) {
        jvm("desktop")
    }
    
    sourceSets {
        if (providers.gradleProperty("include_desktop").get().toBoolean()) {
            val desktopMain by getting
            desktopMain.dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutines.swing)
            }
        }
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.androidx.window)
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.core.splashscreen)
            implementation(libs.koin.android)
            implementation(libs.androidx.core.ktx)
            implementation("com.google.maps.android:maps-compose:6.1.0")
            implementation("com.google.maps.android:maps-compose-utils:6.1.0")
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(compose.materialIconsExtended)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(libs.constraintlayout.compose.multiplatform)
            implementation(libs.kmp.date.time.picker)
            api(libs.calf.ui)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.navigation.compose)
            implementation(libs.firebase.firestore)
            implementation(libs.firebase.auth)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.decompose)
            implementation(libs.decompose.extensions.compose)
            implementation(libs.lifecycle.coroutines)
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.serialization)
            implementation("com.arkivanov.essenty:lifecycle:2.4.0")

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation("io.insert-koin:koin-core:3.4.0")
            implementation("dev.gitlive:firebase-storage:2.1.0")
            implementation(libs.compass.geolocation)
            implementation(libs.compass.geolocation.mobile)
            implementation(libs.compass.permissions.mobile)

            implementation("com.github.skydoves:landscapist-coil3:2.4.5")
        }
        val commonMain by getting
        if (providers.gradleProperty("include_ios").get().toBoolean()) {
            val iosMain by creating {
                dependsOn(commonMain)
            }
            iosMain.dependencies {
                implementation(libs.ktor.client.darwin)
            }
            val iosX64Main by getting {
                dependsOn(iosMain)
            }
            val iosArm64Main by getting {
                dependsOn(iosMain)
            }
            val iosSimulatorArm64Main by getting {
                dependsOn(iosMain)
            }
        }
    }
}

android {
    namespace = "org.artemzhuravlov.clarity"
    compileSdk = 35

    defaultConfig {
        applicationId = "org.artemzhuravlov.clarity"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "org.artemzhuravlov.clarity.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.artemzhuravlov.clarity"
            packageVersion = "1.0.0"
        }
    }
}

compose.resources {
    publicResClass = false
    packageOfResClass = "org.artemzhuravlov.clarity.resources"
    generateResClass = auto
}