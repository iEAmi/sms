plugins {
    id("sms-launcher-conventions")
}

dependencies {
    implementation(platform("com.linecorp.armeria:armeria-bom:1.6.0"))

    implementation("com.linecorp.armeria:armeria")
    implementation("com.linecorp.armeria:armeria-retrofit2")
    implementation("com.linecorp.armeria:armeria-logback")
}

application {
    mainClass.set("me.ahmad.sms.launcher.App")
}
