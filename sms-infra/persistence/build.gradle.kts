plugins {
    id("sms-common-conventions")
}

dependencies {
    implementation(project(":sms-domain"))

    implementation("org.ktorm:ktorm-core:3.3.0")
}