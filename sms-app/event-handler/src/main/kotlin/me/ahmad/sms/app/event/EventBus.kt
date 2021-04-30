package me.ahmad.sms.app.event

import java.io.Closeable

interface EventBus: Closeable {
    fun registerListener(listener: EventListener)
}