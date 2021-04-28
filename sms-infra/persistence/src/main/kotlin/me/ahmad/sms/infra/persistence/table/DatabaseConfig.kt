package me.ahmad.sms.infra.persistence.table

data class DatabaseConfig(
    val url: String,
    val username: String,
    val password: String,
    val schema: String
)