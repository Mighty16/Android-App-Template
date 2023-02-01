applyLibraryPlugins()

androidLibraryConfig(nameSpace = "com.vv.core.network")

dependencies {
    moduleImpl(":utils")
    retrofit()
}