package me.ahmad.sms.app.rest

import com.linecorp.armeria.server.Server
import com.linecorp.armeria.server.docs.DocService
import me.ahmad.sms.app.rest.controller.SmsController
import org.slf4j.Logger
import java.io.Closeable
import java.net.InetSocketAddress
import java.util.concurrent.CompletableFuture

class HttpServer
internal constructor(
    private val logger: Logger,
    private val smsController: SmsController,
    private val exHandler: SmsExceptionHandler,
    private val config: HttpServerConfig
) : Closeable {
    private val server: Server by lazy { init() }

    fun start(): CompletableFuture<Void> = server.start()

    private fun init(): Server {
        val builder = Server.builder()
        builder.http(InetSocketAddress(config.host, config.port))
        builder.annotatedService(config.basePath + "/sms", smsController, exHandler)
        builder.accessLogger(logger)
        builder.serviceUnder("/docs", DocService())

        return builder.build()
    }

    override fun close() {
        logger.info("Stopping http server ...")

        server.stop()
    }
}