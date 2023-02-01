applyAppPlugins()

androidApplicationConfig(
    appId = "com.vv.app",
    appVersionCode = 1,
    appVersionName = "1.0.0"
)

dependencies {

    moduleImpl(":utils")
    moduleImpl(":core-ui")

    impl(Deps.AndroidX.appCompat)
    impl(Deps.AndroidX.lifecycleKtx)
}