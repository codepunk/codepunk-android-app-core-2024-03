import com.android.build.api.dsl.VariantDimension

plugins {
    id(libs.plugins.androidApplication.get().pluginId)
    id(libs.plugins.jetbrainsKotlinAndroid.get().pluginId)
    kotlin(libs.plugins.kapt.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
    kotlin(libs.plugins.serialization.get().pluginId) version libs.versions.serializationPlugin.get()
}

android {
    namespace = "com.codepunk.skeleton"
    compileSdk = libs.versions.compileSdk.get().toInt()

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.codepunk.skeleton"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField(
            type = "long",
            name = "OK_HTTP_CLIENT_CACHE_SIZE",
            value = "10 * 1024 * 1024"
        )

        buildConfigField(
            type = "String",
            name = "NASA_BASE_URL",
            value = "\"https://api.nasa.gov/\""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    applicationVariants.all {
        makeKeys()
        extractLocalProperty(
            project = project.rootProject,
            name = "NASA_API_KEY",
            defaultValue = "DEMO_KEY"
        )
    }
}

dependencies {

    // region Added by Android Studio

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.arrow.core)
    implementation(libs.arrow.core.retrofit)
    implementation(libs.arrow.fx.coroutines)

    // endregion Added by Android Studio

    // region Added by Codepunk

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.converter)
    implementation(libs.okhttp)

    // endregion Added by Codepunk

}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
