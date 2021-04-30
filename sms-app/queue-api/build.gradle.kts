plugins {
    id("sms-common-conventions")
    `java-library`
}

dependencies {
    implementation(project(":sms-domain"))
    api("io.reactivex.rxjava3:rxjava:3.0.13-RC2")
}