plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")

}

kapt {
    correctErrorTypes = true
}

android {
    namespace = "com.project.libum"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.project.libum"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            android.buildFeatures.buildConfig = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "CAPTCHA_API_KEY", "\"${project.findProperty("CAPTCHA_API_KEY")}\"")
            buildConfigField("String", "API_BASE_URL", "\"${project.findProperty("API_BASE_URL")}\"")
            buildConfigField("String", "REGISTER_URL", "\"${project.findProperty("REGISTER_URL")}\"")
            buildConfigField("Boolean", "IS_DEV_MODE", "false")
        }

        debug {
            buildConfigField("String", "CAPTCHA_API_KEY", "\"${project.findProperty("CAPTCHA_API_KEY")}\"")
            buildConfigField("String", "API_BASE_URL", "\"${project.findProperty("API_BASE_URL")}\"")
            buildConfigField("String", "REGISTER_URL", "\"${project.findProperty("REGISTER_URL")}\"")
            buildConfigField("Boolean", "IS_DEV_MODE", "true")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat.v161)
    implementation(libs.material.v1110)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.google.recaptcha)
    implementation(libs.retrofit)
    implementation(libs.junit)
    implementation(libs.gson)
    implementation(libs.converter.gson)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)

    implementation(libs.androidx.core.splashscreen)
    implementation ("com.ibm.icu:icu4j:72.1")

    implementation(libs.hilt.android)
    implementation(libs.androidx.room.ktx)
    kapt(libs.hilt.compiler)
    kapt(libs.androidx.room.compiler)

    implementation("org.jsoup:jsoup:1.15.4")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}