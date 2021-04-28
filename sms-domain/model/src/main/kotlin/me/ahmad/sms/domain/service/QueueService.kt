package me.ahmad.sms.domain.service

import me.ahmad.sms.domain.Sms
import me.ahmad.sms.domain.SmsException

interface QueueService {

    @Throws(SmsException::class)
    fun push(sms: Sms)
}