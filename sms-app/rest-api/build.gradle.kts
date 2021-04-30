plugins {
    id("sms-app-rest-api-conventions")
}

dependencies {
    implementation(project(":sms-app:defs"))
    implementation(project(":sms-domain"))
    implementation("org.jtwig:jtwig-core:5.87.0.RELEASE")
}