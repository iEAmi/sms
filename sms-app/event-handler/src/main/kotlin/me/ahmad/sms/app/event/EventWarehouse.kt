package me.ahmad.sms.app.event

import me.ahmad.sms.domain.Sms

interface EventWarehouse {

    fun store(event: Sms.Event, payload: String)
}