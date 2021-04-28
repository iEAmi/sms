plugins {
    id("sms-common-conventions")
}

dependencies {
    implementation(project(":sms-domain"))

    implementation("org.ktorm:ktorm-core:3.3.0")
    implementation("com.zaxxer:HikariCP:4.0.3")
    implementation("org.ktorm:ktorm-support-postgresql:3.3.0")
    implementation("org.flywaydb:flyway-core:7.8.2")
    runtime("org.postgresql:postgresql:42.2.16")
}