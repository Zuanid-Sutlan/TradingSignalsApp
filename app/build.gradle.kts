plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.devstudio.forexFusion"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.devstudio.forexFusion"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)      // firebase auth
    implementation (libs.play.services.auth)    // Google auth
    implementation(platform(libs.firebase.bom))     // firebase bom
    implementation(libs.firebase.messaging)     // firebase cloud messaging / push notification
    implementation(libs.androidx.runtime.livedata)   // live data
    implementation(libs.firebase.database)
    implementation(libs.firebase.analytics)    // firebase realtime database
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // material components 1
//    implementation(libs.androidx.compose.material)
//    implementation("androidx.compose.material:material:1.6.7")

    // splash screen api
//    implementation("androidx.core:core-splashscreen:1.0.1")

    // extra icons
    implementation("androidx.compose.material:material-icons-extended:1.6.8")

    // viewModel in compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.1")

    // compose navigation
    implementation(libs.navigation.compose)

    // serialization json
    implementation(libs.kotlinx.serialization.json)

    // coil for image loading
    implementation("io.coil-kt:coil-compose:2.6.0")

    // OneSignal for notification
    implementation(libs.onesignal)

    // WorkManager
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // message bar for showing message
//    implementation("com.github.stevdza-san:MessageBarCompose:1.0.8")


}