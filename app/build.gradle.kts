plugins {
    // Plugin padrão do Android
    alias(libs.plugins.android.application)

    // ADICIONADO:
    // Plugin necessário para conectar o app ao Firebase
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.appcontrolepro"
    compileSdk = 36


    defaultConfig {
        applicationId = "com.example.appcontrolepro"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("com.google.android.material:material:1.11.0")

    // ADICIONADO:
    // Firebase Authentication (login e cadastro)
    implementation("com.google.firebase:firebase-auth:22.3.1")

    // ADICIONADO:
    // Firebase Firestore (banco de dados na nuvem)
    implementation("com.google.firebase:firebase-firestore:24.10.3")
    implementation("com.google.firebase:firebase-storage:20.3.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")

}