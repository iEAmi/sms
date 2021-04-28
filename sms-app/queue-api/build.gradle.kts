plugins {
    id("sms-common-conventions")
    `java-library`
}

dependencies {
    implementation(project(":sms-app:common"))
    api("io.reactivex.rxjava3:rxjava:3.0.13-RC2")
}