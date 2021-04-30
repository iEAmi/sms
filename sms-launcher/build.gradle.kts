plugins {
    id("sms-launcher-conventions")
}

dependencies {
    implementation(project(":sms-domain"))
    implementation(project(":sms-app"))
    implementation(project(":sms-infra"))
    implementation("io.github.config4k:config4k:0.4.2")
    implementation("com.github.lalyos:jfiglet:0.0.8")
}

application {
    mainClass.set("me.ahmad.sms.launcher.App")
}