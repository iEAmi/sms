plugins {
    id("sms-common-conventions")
    `java-library`
}

dependencies {
    api(project(":sms-app:common"))
    api(project(":sms-app:rest-api"))
    api(project(":sms-app:queue-api"))
    api(project(":sms-app:event-handler"))
}