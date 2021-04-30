package me.ahmad.sms.infra.persistence.table

data class DatabaseConfig(
    val jdbcUrl: String,
    val serverName: String,
    val databaseName: String,
    val port: Int,
    val username: String,
    val password: String,
    val schema: String
)
