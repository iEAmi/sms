import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.12.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
    kotlinOptions {
        freeCompilerArgs = listOf("-Xinline-classes")
    }
}