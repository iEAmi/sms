plugins {
    id("sms-common-conventions")
}

dependencies {
    implementation(platform("com.linecorp.armeria:armeria-bom:1.6.0"))

    implementation("com.linecorp.armeria:armeria")
    implementation("com.linecorp.armeria:armeria-retrofit2")
    implementation("com.linecorp.armeria:armeria-logback")

    implementation("com.typesafe:config:1.4.1")
}
