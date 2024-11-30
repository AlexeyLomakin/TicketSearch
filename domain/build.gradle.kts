plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {

    //Coroutines
    implementation(libs.kotlinx.coroutines.core)

    //Inject
    implementation(libs.javax.inject)
}
