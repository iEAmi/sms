package me.ahmad.sms.app.event

import java.io.Closeable

class EventListenerStarter internal constructor(
    private val eventBus: EventBus,
    private val eventListener: EventListener,
    private val eventWebSocketServer: EventWebSocketServer
) : Closeable {

    fun start() {
        eventBus.registerListener(eventListener)
        eventBus.registerListener(eventWebSocketServer)
        eventWebSocketServer.start()
    }

    override fun close() {
        eventBus.close()
        eventWebSocketServer.stop()
    }
}