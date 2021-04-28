import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    idea
    eclipse
}

group = "me.ahmad"
version = "1.0.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("org.kodein.di:kodein-di:7.5.0")
    implementation("org.slf4j:slf4j-api:1.7.30")

    testImplementation(kotlin("test-junit"))
    testImplementation(kotlin("kotlin-test-junit"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
    kotlinOptions {
        freeCompilerArgs = listOf("-Xinline-classes")
    }
}
