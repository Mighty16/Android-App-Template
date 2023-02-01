applyLibraryPlugins()

androidLibraryConfig(nameSpace = "com.vv.navigation.cicerone")

dependencies {

    impl(Deps.Navigation.cicerone)
    impl(Deps.AndroidX.fragmentKtx)
    moduleImpl(":core-navigation:api")
}