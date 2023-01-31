applyLibraryPlugins()

androidLibraryConfig(nameSpace = "com.vv.core.ui")

dependencies {
    impl(Deps.AndroidX.coreKtx)
    impl(Deps.AndroidX.appCompat)
    impl(Deps.AndroidX.material)
}