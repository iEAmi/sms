package me.ahmad.sms.app.queue

import me.ahmad.sms.domain.Provider
import me.ahmad.sms.domain.Sms

interface SmsDispatcher {

    fun dispatch(sms: Sms, provider: Provider): Boolean
}