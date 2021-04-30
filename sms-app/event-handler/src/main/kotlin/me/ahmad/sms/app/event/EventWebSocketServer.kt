package me.ahmad.sms.app.event

import com.google.gson.Gson
import me.ahmad.sms.domain.Provider
import me.ahmad.sms.domain.Sms
import me.ahmad.sms.domain.event.Event
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import org.slf4j.Logger
import java.net.InetSocketAddress

internal class EventWebSocketServer(
    private val logger: Logger,
    private val gson: Gson
) : WebSocketServer(InetSocketAddress("127.0.0.1", 8081), 1), EventListener {
    override fun onStart() {
        logger.info("Server started on $address")
    }

    override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
        logger.info("client connected : ${conn?.remoteSocketAddress} ${handshake?.resourceDescriptor}")
        conn?.send("You are connected.")
    }

    override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
        logger.warn("connection closed : $code $reason")
    }

    override fun onMessage(conn: WebSocket?, message: String?) {
        // ignore client messages
    }

    override fun onError(conn: WebSocket?, ex: Exception?) {
        logger.warn("$ex")
    }

    override fun onEvent(event: Event<*>) {
        val entity = when (event) {
            is Sms.Event -> "SMS"
            is Provider.Event -> "PROVIDER"
            else -> "UNKNOWN"
        }
        val type = event.javaClass.simpleName

        val message = gson.toJson(EventMessage(entity, type, event))
        broadcast(message)
    }

    private data class EventMessage(val entity: String, val type: String, val event: Event<*>)
}