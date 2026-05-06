plugins {

    // PLUGIN ANDROID
    id("com.android.application")

    // FIREBASE
    id("com.google.gms.google-services")

    }

android {

    namespace = "com.example.appcontrolepro"

    compileSdk = 35

    defaultConfig {

        applicationId = "com.example.appcontrolepro"

        minSdk = 24

        targetSdk = 35

        versionCode = 1

        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        release {

            isMinifyEnabled = false

            proguardFiles(

                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),

                "proguard-rules.pro"
            )
        }
    }

    compileOptions {

        sourceCompatibility =
            JavaVersion.VERSION_11

        targetCompatibility =
            JavaVersion.VERSION_11
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")

    implementation("com.google.android.material:material:1.11.0")

    implementation("androidx.activity:activity:1.9.0")

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // FIREBASE AUTH
    implementation("com.google.firebase:firebase-auth:22.3.1")

    // FIRESTORE
    implementation("com.google.firebase:firebase-firestore:24.10.3")

    // STORAGE
    implementation("com.google.firebase:firebase-storage:20.3.0")

    // GLIDE
    implementation("com.github.bumptech.glide:glide:4.16.0")

    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // TESTES
    testImplementation("junit:junit:4.13.2")

    androidTestImplementation(
        "androidx.test.ext:junit:1.2.1"
    )

    androidTestImplementation(
        "androidx.test.espresso:espresso-core:3.6.1"
    )

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:34.13.0"))


    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")


    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries

    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    //cloudinary

    implementation("com.cloudinary:cloudinary-android:2.3.1")
}