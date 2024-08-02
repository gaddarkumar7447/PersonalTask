import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.serializatoin)

    id("app.cash.sqldelight") version "2.0.2"


}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            //isStatic = true
        }
    }
    
    sourceSets {

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            api("androidx.appcompat:appcompat:1.6.1")
            api("androidx.core:core-ktx:1.10.1")
            implementation(libs.androidx.preference.ktx)
            implementation("app.cash.sqldelight:android-driver:2.0.2")


        }

        iosMain.dependencies {
            implementation("app.cash.sqldelight:native-driver:2.0.2")


        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.koin.compose)
            //implementation(libs.koin.composeVM)
            implementation(libs.kamel.image)

            implementation(libs.navigation.compose)

            api("io.insert-koin:koin-core:3.4.3")
            implementation("io.insert-koin:koin-compose:1.1.0")
            api("moe.tlaster:precompose:1.5.10")
            api("moe.tlaster:precompose-viewmodel:1.5.10")
            api("moe.tlaster:precompose-koin:1.5.10")

            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.coroutines)
            implementation("app.cash.sqldelight:coroutines-extensions:2.0.2")

            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")

            implementation("com.russhwolf:multiplatform-settings:1.1.1")

        }
    }

    sqldelight{
        databases{
            create("AppDatabase"){
                this.packageName.set("app_db")
                srcDirs("src/commonMain/sqldelight")
            }
        }
    }
}

android {
    namespace = "org.example.personaltask"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.example.personaltask"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
        implementation(libs.kotlinx.coroutines.android)
    }
}

