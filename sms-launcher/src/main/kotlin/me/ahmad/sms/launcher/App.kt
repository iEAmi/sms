package me.ahmad.sms.launcher

import com.github.lalyos.jfiglet.FigletFont
import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import me.ahmad.sms.app.`sms-app-module`
import me.ahmad.sms.app.rest.HttpServerConfig
import me.ahmad.sms.domain.`sms-domain-model-module`
import me.ahmad.sms.infra.`sms-infra-module`
import me.ahmad.sms.infra.persistence.table.DatabaseConfig
import org.kodein.di.*
import org.slf4j.ILoggerFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object App {

    @JvmStatic
    fun main(args: Array<String>) {
        println(FigletFont.convertOneLine("SMS"))
        val cfg = ConfigFactory.defaultApplication().resolve()

        val di = DI {
            bindConstant("HttpServerConfig") { cfg.extract<HttpServerConfig>("sms.server") }
            bindConstant("DatabaseConfig") { cfg.extract<DatabaseConfig>("sms.database") }

            bind { singleton { Launcher(instance(), instance(), instance(), instance()) } }
            bind<ILoggerFactory> { singleton { LoggerFactory.getILoggerFactory() } }
            bind<Logger> { factory { name: String -> instance<ILoggerFactory>().getLogger(name) } }

            import(`sms-app-module`)
            import(`sms-infra-module`)
            import(`sms-domain-model-module`)
        }

        val launcher: Launcher by di.instance()
        launcher.start()
    }
}

