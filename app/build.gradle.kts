plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.rukia"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.rukia.phone"
        minSdk = 28
        targetSdk = 37
        versionCode = 1
        versionName = "0.1"
    }
    buildFeatures { compose = true }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2026.09.00"))
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.activity:activity-compose:1.13.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")
    implementation("com.bladecoder.ink:blade-ink:1.3.2")
    implementation("com.bladecoder.ink:blade-ink-compiler:1.3.2")
    implementation("androidx.work:work-runtime-ktx:2.12.0")
    implementation("org.osmdroid:osmdroid-android:6.1.20")
    testImplementation(kotlin("test-junit"))
}

// The story tests read the case files in src/main/assets: rerun them when those change, not only when code does.
tasks.withType<Test>().configureEach { inputs.dir("src/main/assets").withPropertyName("caseAssets") }
