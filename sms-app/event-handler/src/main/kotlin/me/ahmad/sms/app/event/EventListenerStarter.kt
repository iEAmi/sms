package me.ahmad.sms.app.event

import java.io.Closeable

class EventListenerStarter(
    private val eventBus: EventBus,
    private val eventListener: EventListener
) : Closeable by eventBus {

    fun start() {
        eventBus.setListener(eventListener)
    }
}