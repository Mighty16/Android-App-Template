import com.android.build.api.dsl.ApkSigningConfig
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.BuildType
import com.android.build.gradle.internal.dsl.DefaultConfig
import org.gradle.api.JavaVersion
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import org.gradle.api.plugins.PluginAware
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.version
import java.io.File

val Project.android: BaseExtension
    get() = extensions.findByName("android") as? BaseExtension
        ?: error("Project '$name' is not an Android module")

val Project.buildTypes: NamedDomainObjectContainer<BuildType>
    get() = android.buildTypes

fun Project.androidLibraryConfig(
    nameSpace: String,
    defaultConfigExtensions: (DefaultConfig.() -> Unit)? = null,
    buildTypesConfig: (NamedDomainObjectContainer<BuildType>.() -> Unit)? = null
) {

    repositories {
        google()
        mavenCentral()
    }

    android.run {
        namespace = nameSpace
        compileSdkVersion(Versions.compileSdkVersion)
        defaultConfig {
            minSdk = Versions.minSdkVersion
            targetSdk = Versions.targetSdkVersion
            testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
            defaultConfigExtensions?.invoke(this)
        }

        buildTypes {
            if (buildTypesConfig != null) {
                buildTypesConfig.invoke(this)
            } else {
                debug {
                    isMinifyEnabled = false
                }
                release {
                    isMinifyEnabled = true
                    proguard(getDefaultProguardFile("proguard-android.txt"))
                }
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        sourceSets {
            val kotlinAdditionalSourceSets = project.file("src/main/kotlin")
            findByName("main")?.java?.srcDirs(kotlinAdditionalSourceSets)
        }
        packagingOptions {
            resources.excludes += "META-INF/main.kotlin_module"
            resources.excludes += "DebugProbesKt.bin"
        }

        viewBinding {
            isEnabled = true
        }


    }
}

fun Project.androidApplicationConfig(
    appId: String,
    appVersionCode: Int,
    appVersionName: () -> String
) {
    androidApplicationConfig(
        appId = appId,
        appVersionCode = appVersionCode,
        appVersionName = appVersionName()
    )
}

fun Project.androidApplicationConfig(
    appId: String,
    appVersionCode: Int,
    appVersionName: String,
    buildTypesConfig: (NamedDomainObjectContainer<BuildType>.() -> Unit)? = null
) {
    androidLibraryConfig(
        nameSpace = appId,
        defaultConfigExtensions = {
            applicationId = appId
            versionCode = appVersionCode
            versionName = appVersionName
        }, buildTypesConfig = buildTypesConfig
    )
}

fun Project.applyAppPlugins(pluginsExtension: (PluginContainer.() -> Unit)? = null) {
    plugins.run {
        apply("com.android.application")
        apply("kotlin-android")
        pluginsExtension?.invoke(this)
    }
}

fun Project.applyLibraryPlugins(pluginsExtension: (PluginContainer.() -> Unit)? = null) {
    plugins.run {
        apply("com.android.library")
        apply("kotlin-android")
        pluginsExtension?.invoke(this)
    }
}

fun PluginContainer.parcelize() {
    this.run {
        apply("kotlin-parcelize")
    }
}

fun NamedDomainObjectContainer<BuildType>.debug(config: (BuildType.() -> Unit)? = null) {
    return getByName("debug").run {
        config?.invoke(this)
    }
}

fun NamedDomainObjectContainer<BuildType>.release(config: (BuildType.() -> Unit)? = null) {
    return getByName("release").run {
        config?.invoke(this)
    }
}

fun Project.buildType(
    name: String,
    config: (BuildType.() -> Unit)? = null
): BuildType {
    return android.buildTypes.create(name).apply {
        config?.invoke(this)
    }
}

fun BuildType.proguard(proguardFile: File) {
    proguardFiles(proguardFile, "proguard-rules.pro")
    consumerProguardFiles("consumer-rules.pro")
}

fun BuildType.stringField(name: String, value: String) {
    buildConfigField("String", name, "\"$value\"")
}

fun BuildType.signConfig(config: () -> ApkSigningConfig) {
    signingConfig = config.invoke()
}

fun DefaultConfig.stringField(name: String, value: String){
    buildConfigField("String",name,"\"$value\"")
}

fun BuildType.signConfig(config: ApkSigningConfig) {
    signingConfig = config
}

fun Project.enableBuildConfig(defaultConfigExtensions: (DefaultConfig.() -> Unit)? = null) {
    android.buildFeatures.buildConfig = true
    defaultConfigExtensions?.invoke(android.defaultConfig)
}
