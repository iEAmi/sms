plugins {
    id("sms-common-conventions")
}

dependencies {
    implementation(project(":sms-domain"))
    implementation("org.java-websocket:Java-WebSocket:1.5.1")
    implementation("com.google.code.gson:gson:2.8.6")
}