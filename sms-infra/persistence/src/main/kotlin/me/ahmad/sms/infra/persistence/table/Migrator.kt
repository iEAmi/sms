package me.ahmad.sms.infra.persistence.table

import org.flywaydb.core.Flyway

class Migrator internal constructor(private val cfg: DatabaseConfig) {

    fun migrateUp() {
        val flyway = Flyway.configure()
            .dataSource(cfg.url, cfg.username, cfg.password)
            .defaultSchema(cfg.schema)
            .load()
        flyway.migrate()
    }
}