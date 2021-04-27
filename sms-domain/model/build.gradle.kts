plugins {
    id("sms-common-conventions")
    `java-library`
}

dependencies {
    api(project(":sms-domain:common"))
}
