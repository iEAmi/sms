plugins {
    id("sms-common-conventions")
}

dependencies {
    implementation(project(":sms-domain"))
    implementation(project(":sms-app"))
}