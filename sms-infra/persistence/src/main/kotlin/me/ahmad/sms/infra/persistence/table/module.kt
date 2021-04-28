package me.ahmad.sms.infra.persistence.table

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.kodein.di.*
import org.ktorm.database.Database
import org.ktorm.logging.Slf4jLoggerAdapter
import org.ktorm.support.postgresql.PostgreSqlDialect
import org.slf4j.Logger
import java.util.concurrent.TimeUnit
import javax.sql.DataSource

internal val `table-module` = DI.Module("table-module") {
    bind { singleton { hikariCP(instance("DatabaseConfig")) } }
    bind {
        singleton {
            Database.connect(
                dataSource = instance(),
                dialect = PostgreSqlDialect(),
                logger = Slf4jLoggerAdapter(instance(arg = "Database"))
            )
        }
    }
}

private fun hikariCP(cfg: DatabaseConfig): DataSource {
    val config = HikariConfig()
    config.jdbcUrl = cfg.url
    config.username = cfg.username
    config.password = cfg.password
    config.schema = cfg.schema
    config.poolName = "sms-hikari-jdbc-connection-pool"
    config.connectionTimeout = TimeUnit.SECONDS.toMillis(10)
    config.idleTimeout = TimeUnit.MINUTES.toMillis(2)
    config.maximumPoolSize = 10
    config.addDataSourceProperty("cachePrepStmts", "true")
    config.addDataSourceProperty("prepStmtCacheSize", "250")
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")

    return HikariDataSource(config)
}