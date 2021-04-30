plugins {
    id("sms-app-rest-api-conventions")
}

dependencies {
    implementation(project(":sms-app:defs"))
    implementation(project(":sms-domain"))
}