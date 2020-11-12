object Sdk {
    const val compile = 30
    const val target = 30
    const val min = 22
}

object Versions {
    const val kotlin = "1.4.10"
    const val gradle = "4.1.1"
}

object Dep {
    object Kotlin {
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3"
    }

    object Main {
        const val gson = "com.google.code.gson:gson:2.8.6"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.3.2"
        const val appcompat = "androidx.appcompat:appcompat:1.2.0"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val legacy = "androidx.legacy:legacy-support-v4:1.0.0"

        const val _navigation_version = "2.3.1"
        const val navigation = "androidx.navigation:navigation-fragment-ktx:$_navigation_version"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:$_navigation_version"

        const val _lifecycle_version = "2.2.0"
        const val lifecycleExt = "androidx.lifecycle:lifecycle-extensions:$_lifecycle_version"
        const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$_lifecycle_version"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.0-beta01"
    }

    object Retrofit {
        const val _retrofit_version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$_retrofit_version"
        const val converter = "com.squareup.retrofit2:converter-gson:$_retrofit_version"
        const val interceptor = "com.squareup.okhttp3:logging-interceptor:4.9.0"
    }

    object GlideImg {
        const val _glide_version = "4.11.0"
        const val glide = "com.github.bumptech.glide:glide:$_glide_version"
        const val okhttp3 = "com.github.bumptech.glide:okhttp3-integration:$_glide_version"
        const val compiler = "com.github.bumptech.glide:compiler:$_glide_version"
    }

    object Material {
        const val material = "com.google.android.material:material:1.2.1"
    }

    object Test {
        const val junit = "junit:junit:4.13.1"
        const val extJunit = "androidx.test.ext:junit:1.1.2"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
    }
}