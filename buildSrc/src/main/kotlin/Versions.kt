object Versions {

    const val compileSdkVersion = 33
    const val minSdkVersion = 23
    const val targetSdkVersion = 33

    const val kotlinVersion = "1.7.20"
    const val androidPluginVersion = "7.1.2"

    const val androidToolsGradle = "com.android.tools.build:gradle:7.4.0"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"

}

object Deps {

    object AndroidX {

        private const val appCompatVer = "1.6.0"
        private const val lifecycleKtxVer = "2.3.1"
        private const val coreKtxVer = "1.8.0"
        private const val materialVer = "1.5.0"

        // Test ver
        private const val espressoVer = "3.5.1"

        const val appCompat = "androidx.appcompat:appcompat:$appCompatVer"
        const val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleKtxVer"
        const val coreKtx = "androidx.core:core-ktx:$coreKtxVer"
        const val material = "com.google.android.material:material:$materialVer"

        // Tests
        const val espressoCore = "androidx.test.espresso:espresso-core:$espressoVer"
        const val junitExt = "androidx.test.ext:junit:1.1.5"

    }

    object Network{

        private const val retrofitVer = "2.9.0"

        private const val logInterceptorVer = "4.10.0"

        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVer"

        const val logInterceptor = "com.squareup.okhttp3:logging-interceptor:logInterceptorVer"
    }

    object Test {

        private const val junitVer = "4.13.2"

        const val junit = "junit:junit:$junitVer"

    }
}