plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

androidExtensions {
    isExperimental = true
}

android {
    compileSdkVersion(Dependencies.Android.compileSdk)

    defaultConfig {
        applicationId = Dependencies.Android.applicationId
        minSdkVersion(Dependencies.Android.minSdk)
        targetSdkVersion(Dependencies.Android.targetSdk)
        versionCode = Dependencies.Android.versionCode
        versionName = Dependencies.Android.version

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
    }

    buildFeatures {
        dataBinding = true
    }

    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(Dependencies.Libs.stdlib)
    implementation(Dependencies.Libs.coreKtx)

    implementation(Dependencies.Libs.material)
    implementation(Dependencies.Libs.constraintLayout)

    // Databinding
    implementation(Dependencies.Libs.databinding)

    // Jetpack
    implementation(Dependencies.Libs.roomRuntime)
    implementation(Dependencies.Libs.roomKtx)
    kapt(Dependencies.Libs.roomCompiler)
    implementation(Dependencies.Libs.lifecycleViewModelKtx) //viewModelScope
    implementation(Dependencies.Libs.lifecycleRuntimeKtx) //lifeCycleScope
    implementation(Dependencies.Libs.lifecycleLiveDataKtx) //livedata { ...}
    implementation(Dependencies.Libs.navigationFragmentKtx)
    implementation(Dependencies.Libs.navigationUiKtx)

    // Coroutines
    implementation(Dependencies.Libs.coroutinesCore)
    implementation(Dependencies.Libs.coroutinesAndroid)

    // Koin
    implementation(Dependencies.Libs.koinViewModel)

    // LiveData extensions
    implementation(Dependencies.Libs.livedataExtensions)

    // Networking
    implementation(Dependencies.Libs.retrofit)
    implementation(Dependencies.Libs.retrofitConverter)
    implementation(Dependencies.Libs.okhttp3)

    // Loggin
    implementation(Dependencies.Libs.timber)

    // Coil
    implementation(Dependencies.Libs.coil)
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
    kotlinOptions.jvmTarget = "1.8"
}
