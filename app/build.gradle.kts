plugins {
    id(Plugins.application)
    kotlin(Plugins.kotlin_android)
    kotlin(Plugins.kotlin_kapt)
    id(Plugins.kotlin_parcelize)
}
android {
    compileSdkVersion(SdkVersions.compileSdk)
    defaultConfig {
        ndk { abiFilters.add(Invariant.armeabiV7a) }
        applicationId(Plugins.applicationId)
        minSdkVersion(SdkVersions.minSdk)
        targetSdkVersion(SdkVersions.targetSdk)
        versionCode(SdkVersions.versionCode)
        versionName(SdkVersions.versionName)
        multiDexEnabled = true
    }
    buildFeatures {
        viewBinding = true
    }
    signingConfigs {
        create(Invariant.RELEASE) {
//            keyAlias = RELEASE_KEY_ALIAS
//            keyPassword = RELEASE_KEY_PASSWORD
//            storeFile = file(RELEASE_FILE_PATH)
//            storePassword = RELEASE_STORE_PASSWORD
        }
        getByName(Invariant.DEBUG) {
//            keyAlias = RELEASE_KEY_ALIAS
//            keyPassword = RELEASE_KEY_PASSWORD
//            storeFile = file(RELEASE_FILE_PATH)
//            storePassword = RELEASE_STORE_PASSWORD
        }
    }
    buildTypes {
        getByName(Invariant.RELEASE) {
            signingConfig = signingConfigs[Invariant.RELEASE]
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(
                    Invariant.defaultProguardFileName
                ),
                Invariant.proguardFileName
            )
        }
        getByName(Invariant.DEBUG) {
            isMinifyEnabled = false
            signingConfig = signingConfigs[Invariant.DEBUG]
            proguardFiles(
                getDefaultProguardFile(
                    Invariant.defaultProguardFileName
                ),
                Invariant.proguardFileName
            )
        }
    }
    compileOptions {
        encoding = Invariant.UTF_8
        sourceCompatibility(SdkVersions.javaVersion)
        targetCompatibility(SdkVersions.javaVersion)
    }
    packagingOptions {
        exclude(Invariant.kotlinModule)
    }
    kotlinOptions {
        jvmTarget = SdkVersions.kotlinJavaTarget
    }
}
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dep.Kotlin.kotlin)
    api(project(":basis_bearer"))
    api(project(":library_db"))
    api(project(":library_entity"))
}