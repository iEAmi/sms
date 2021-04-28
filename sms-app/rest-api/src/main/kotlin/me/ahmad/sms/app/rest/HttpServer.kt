package me.ahmad.sms.app.rest

import com.linecorp.armeria.server.Server
import me.ahmad.sms.app.rest.controller.SmsController
import org.slf4j.Logger
import java.io.Closeable
import java.net.InetSocketAddress

class HttpServer
internal constructor(
    private val logger: Logger,
    private val smsController: SmsController,
    private val exHandler: SmsExceptionHandler,
    private val config: HttpServerConfig
) : Closeable {
    private val server: Server by lazy { init() }

    fun start() = server.start()

    private fun init(): Server {
        val builder = Server.builder()
        builder.http(InetSocketAddress(config.host, config.port))
        builder.annotatedService(config.basePath, smsController, exHandler)

        return builder.build()
    }

    override fun close() {
        logger.info("Stopping http server ...")

        server.stop()
    }
}