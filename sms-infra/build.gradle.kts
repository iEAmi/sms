plugins {
    id("sms-common-conventions")
    `java-library`
}

dependencies {
    api(project(":sms-infra:persistence"))
    api(project(":sms-infra:queue"))
    api(project(":sms-infra:dispatcher"))
}
