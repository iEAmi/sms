plugins {
    id("sms-common-conventions")
    `java-library`
}

dependencies {
    implementation(project(":sms-domain"))
}