object Dependencies {

    object Android {
        val applicationId = "com.tkuhn.recipefinder"
        val version = "1.0"
        val versionCode = 1
        val minSdk = 21
        val compileSdk = 29
        val targetSdk = 29
    }

    object Versions {
        val gradlePlugin = "4.0.0-beta01"
        val kotlin = "1.3.61"
        val material = "1.2.0-alpha04"
        val navigation = "2.3.0-alpha01"
        val coreKtx = "1.2.0"
        val constraintLayout = "2.0.0-beta4"
        val coroutines = "1.3.3"
        val lifecycle = "2.2.0"
        val retrofit = "2.7.1"
        val koin = "2.1.0-beta-1"
        val okhttp3 = "4.3.1"
        val room = "2.2.3"
        val timber = "4.7.1"
        val databindingKtx = "2.0.2"
        val livedataExtensions = "1.3.0"
        val coil = "0.9.5"
        val legacyV4 = "1.0.0"
    }

    object BuildPlugins {
        val gradlePlugin = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
        val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        val safeArgsPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    }

    object Libs {
        val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
        val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        val material = "com.google.android.material:material:${Versions.material}"
        val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        val legacySupportV4 = "androidx.legacy:legacy-support-v4:${Versions.legacyV4}"

        // Databinding
        val databinding = "com.github.wada811:DataBinding-ktx:${Versions.databindingKtx}"

        // Jetpack
        val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
        val roomKtx = "androidx.room:room-ktx:${Versions.room}"
        val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
        val lifecycleViewModelKtx =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}" //viewModelScope
        val lifecycleRuntimeKtx =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}" //lifeCycleScope
        val lifecycleLiveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}" //livedata { ...}
        val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

        // Coroutines
        val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

        // Koin
        val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

        // Livedata extensions
        val livedataExtensions = "com.snakydesign.livedataextensions:lives:${Versions.livedataExtensions}"

        // Networking
        val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        val retrofitConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        val okhttp3 = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3}"

        // Logging
        val timber = "com.jakewharton.timber:timber:${Versions.timber}"

        // Coil
        val coil = "io.coil-kt:coil:${Versions.coil}"
    }
}