package me.ahmad.sms.app.event

import me.ahmad.sms.domain.event.Event

interface EventListener {
    fun onEvent(event: Event<*>)
}