
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)


}

android {
    namespace = "ar.edu.unicen.seminarioandroid2026"
    compileSdk = 34

    defaultConfig {
        applicationId = "ar.edu.unicen.seminarioandroid2026"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }


    // 🛠️ CAMBIO 1: Leer la API Key desde local.properties
    val properties = Properties()
    val localPropertiesFile = project.rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        properties.load(localPropertiesFile.inputStream())
    }
    val apiKey = properties.getProperty("TMDB_API_KEY") ?: ""

    // Genera la variable accesible desde código Kotlin (BuildConfig.TMDB_API_KEY)
    buildConfigField("String", "TMDB_API_KEY", "\"$apiKey\"")
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
    // 1. Habilitar Compose
    buildFeatures {
        compose = true
        buildConfig = true
    }

    // 3. Actualizar a Java 17 (Recomendado)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    // Red / Networking (Retrofit + OkHttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    //hilt y kapt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(platform(libs.androidx.compose.bom))
    // Librerías base de Compose
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3) // Material 3

    // Integración con Activity y ViewModels
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Coil para cargar imágenes desde URL (TMDB)
    implementation("io.coil-kt:coil-compose:2.6.0")

    //Paginado
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.hilt.navigation.compose)
    // Testing

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}