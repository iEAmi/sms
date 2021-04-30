plugins {
    id("sms-common-conventions")
}

dependencies {
    implementation(project(":sms-domain"))
    implementation(project(":sms-app:queue-api"))
    implementation(project(":sms-app:event-handler"))

    implementation("io.reactivex.rxjava3:rxjava:3.0.13-RC2")
}