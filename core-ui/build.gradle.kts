applyLibraryPlugins()

androidLibraryConfig(nameSpace = "com.vv.core.ui")

dependencies {

    moduleImpl(":utils")

    impl(Deps.AndroidX.coreKtx)
    impl(Deps.AndroidX.appCompat)
    impl(Deps.AndroidX.material)
}