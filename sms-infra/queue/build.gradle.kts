plugins {
    id("sms-common-conventions")
}

dependencies {
    implementation(project(":sms-domain"))
    implementation(project(":sms-app:queue-api"))
    implementation(project(":sms-app:event-handler"))
}