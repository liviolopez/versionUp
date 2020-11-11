plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(Sdk.compile)

    signingConfigs {
        register("release") {
            storeFile = file("../certs/${KeyStore.storeFile}")
            keyAlias = KeyStore.keyAlias
            keyPassword = KeyStore.keyPassword
            storePassword = KeyStore.storePassword
        }
    }

    defaultConfig {
        applicationId = "dev.all4.drinks"
        minSdkVersion(Sdk.min)
        targetSdkVersion(Sdk.target)

        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }

        named("debug") {
            isDebuggable = true
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions { jvmTarget = "1.8" }

    buildFeatures.dataBinding = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Dep.Kotlin.kotlin)

    implementation(Dep.AndroidX.coreKtx)
    implementation(Dep.AndroidX.appcompat)
    implementation(Dep.AndroidX.constraintlayout)
    implementation(Dep.AndroidX.navigation)
    implementation(Dep.AndroidX.navigationUi)
    implementation(Dep.AndroidX.legacy)

    implementation(Dep.Material.material)

    testImplementation(Dep.Test.junit)
    androidTestImplementation(Dep.Test.extJunit)
    androidTestImplementation(Dep.Test.espressoCore)
}
