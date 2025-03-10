import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.ksp)
}

val secretPropertiesFile: File = rootProject.file("secrets.properties")
val sitProps = Properties()
secretPropertiesFile.inputStream().use { input ->
    sitProps.load(input)
}

android {
    namespace = "com.example.expenseTracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.expense_tracker"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "prefPassword", sitProps.getProperty("prefPassword"))
        buildConfigField("String", "prefName", sitProps.getProperty("prefName"))
        buildConfigField("String", "API_KEY", sitProps.getProperty("API_KEY"))
        buildConfigField("String", "baseUrl", sitProps.getProperty("baseUrl"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
        viewBinding = true
    }
    // if true, check all issues, including those that are off by default
    // if true, stop the gradle build if errors are found
    // if true, treat all warnings as errors
    lint {
        checkAllWarnings = true // if true, check all issues, including those that are off by default
        abortOnError = true // if true, stop the gradle build if errors are found
        warningsAsErrors = false // if true, treat all warnings as errors
        baseline = file("lint-baseline.xml")
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
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.room.ktx)
    // test
    androidTestImplementation(libs.androidx.ui.test.junit4)
// Needed for createComposeRule, but not createAndroidComposeRule:
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    // For runBlockingTest, CoroutineDispatcher etc.
    testImplementation(libs.kotlinx.coroutines.test)
    // For InstantTaskExecutorRule
    testImplementation(libs.androidx.core.testing)
    // Core library
    implementation(libs.lottie.compose)
    implementation(libs.shimmer.compose)
// AndroidJUnitRunner and JUnit Rules
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestUtil(libs.androidx.test.orchestrator)

// Assertions
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.truth)
    androidTestImplementation(libs.google.test.truth)

// Espresso dependencies
    androidTestImplementation(libs.test.espresso.core)
    androidTestImplementation(libs.test.espresso.contrib)
    androidTestImplementation(libs.test.espresso.intents)
    androidTestImplementation(libs.test.espresso.accessibility)
    androidTestImplementation(libs.test.idling.concurrent)

    implementation(libs.mockito.kotlin)
    implementation(libs.androidx.ui)
    // Hilt
    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.dagger.compiler)
    ksp(libs.dagger.hilt.android.compiler)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)
    implementation(libs.converter.scalars)
    implementation(libs.encrypted.preferences)
    // Glide
    implementation(libs.landscapist.glide)

    implementation(libs.androidx.navigation.compose)
    // room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.androidx.room.testing)
    ksp(libs.androidx.room.compiler)
    implementation(kotlin("test"))
    // splash screen
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.compose)
}
