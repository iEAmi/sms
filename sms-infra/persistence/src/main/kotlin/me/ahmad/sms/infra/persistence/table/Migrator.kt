package me.ahmad.sms.infra.persistence.table

import org.flywaydb.core.Flyway

class Migrator internal constructor(private val cfg: DatabaseConfig) {

    fun migrateUp() {
        val flyway = Flyway.configure()
            .schemas(cfg.schema)
            .dataSource(cfg.jdbcUrl, cfg.username, cfg.password)
            .load()
        flyway.migrate()
    }
}