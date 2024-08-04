import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.serializatoin)
    alias(libs.plugins.sqldelight)
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
            api(libs.androidx.appcompat)
            api(libs.androidx.core.ktx)
            implementation(libs.androidx.preference.ktx)
            implementation(libs.android.extension.sqldelight)
        }

        iosMain.dependencies {
            implementation(libs.ios.extension.sqldelight)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.koin.compose)
            //implementation(libs.koin.composeVM)
            implementation(libs.kamel.image)

            implementation(libs.navigation.compose)

            api(libs.koin.core)
            api(libs.precompose)
            api(libs.precompose.viewmodel)
            api(libs.precompose.koin)

            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.coroutines)

            // for the sqldelight (offline database)
            implementation(libs.common.extension.sqldelight)

            // date and time for kmp
            implementation(libs.kotlinx.date.time)

            // for the sharedPref - settings
            implementation(libs.multiplatform.settings)

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
dependencies {
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.core.i18n)
}

