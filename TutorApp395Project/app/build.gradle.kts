plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "com.example.tutorapp395project"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tutorapp395project"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    testOptions {
        reportDir = file("${buildDir}/test_reports").toString()

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
        viewBinding = true
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

    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    val navVersion = "2.7.6"
    val composeVersion = "1.7.0-alpha01"
    val room_version = "2.6.0"

    implementation("androidx.navigation:navigation-compose:$navVersion")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.4.+")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.6.0")
    implementation ("androidx.navigation:navigation-compose:2.4.0-alpha04")
    implementation("androidx.preference:preference:1.2.1")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.test.espresso:espresso-intents:3.5.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("io.mockk:mockk-android:1.12.0")
    androidTestImplementation("androidx.test:runner:1.4.2")
    androidTestImplementation("androidx.test:rules:1.4.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    // retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    // coil
    implementation("io.coil-kt:coil-compose:1.4.0")
    // Dagger
    implementation ("com.google.dagger:dagger:2.40") // Core dagger library
    annotationProcessor ("com.google.dagger:dagger-compiler:2.40") // Dagger annotation processor
    implementation ("com.google.dagger:dagger-android:2.40") // Dagger Android support
    implementation ("com.google.dagger:dagger-android-support:2.40") // Dagger Android support library
    annotationProcessor ("com.google.dagger:dagger-android-processor:2.40") // Dagger Android annotation processor

    // Room
    annotationProcessor("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-runtime:2.6.0")
    kapt("androidx.room:room-compiler:$room_version")
    androidTestImplementation("androidx.room:room-testing:2.6.0")
    //ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:2.6.1")
}

