
import java.util.*

plugins {
    id("com.android.library")
    id("maven-publish")
    kotlin("android")
}

val versionName = "4.0.1"


android {
    compileSdk = 31

    defaultConfig {
        minSdk = 16
    }


    buildTypes {
        getByName("release") {
            consumerProguardFiles("proguard-rules.pro")
        }
    }


    compileOptions {
        kotlinOptions.freeCompilerArgs = ArrayList<String>().apply {
            add("-module-name")
            add("com.github.CymChad.brvah")
            add("-Xjvm-default=all")
        }
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    namespace = "com.chad.library"

}


dependencies {
    implementation ("com.google.android.material:material:1.6.0")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation ("androidx.databinding:databinding-runtime:8.0.0")
}

