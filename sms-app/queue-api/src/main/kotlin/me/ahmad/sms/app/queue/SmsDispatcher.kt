package me.ahmad.sms.app.queue

import me.ahmad.sms.domain.Sms

interface SmsDispatcher {

    fun dispatch(sms: Sms): Boolean
}