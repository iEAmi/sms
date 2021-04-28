plugins {
    id("sms-common-conventions")
    `java-library`
}

dependencies {
    api(project(":sms-domain"))
    api(project(":sms-app:defs"))
}