package me.ahmad.sms.launcher

import com.github.lalyos.jfiglet.FigletFont
import me.ahmad.sms.app.queue.QueueStarter
import me.ahmad.sms.app.rest.HttpServer
import me.ahmad.sms.infra.persistence.table.Migrator


internal class Launcher(
    private val migrator: Migrator,
    private val httpServer: HttpServer,
    private val queueStarter: QueueStarter
) {

    fun start() {
        println(FigletFont.convertOneLine("SMS"))
        migrator.migrateUp()

        addShutdownHook()

        queueStarter.start()
        httpServer.start().join()
    }

    private fun addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(Thread {
            httpServer.close()
            queueStarter.close()
        })
    }
}