package me.ahmad.sms.app.queue

import me.ahmad.sms.domain.Sms

interface QueueContext {
    val sms: Sms

    fun done()

    fun reject()

    fun requeue(sms: Sms)
}