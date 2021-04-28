package me.ahmad.sms.app

import me.ahmad.sms.domain.PhoneNumber
import me.ahmad.sms.domain.SmsException.WrappedException
import me.ahmad.sms.domain.service.QueueSmsService
import me.ahmad.sms.domain.service.QueueSmsService.ProviderNotFoundException


class SmsFacadeService internal constructor(
    private val queueSmsService: QueueSmsService
) {

    @Throws(WrappedException::class, ProviderNotFoundException::class)
    fun queue(phoneNumber: PhoneNumber, text: String) = queueSmsService.queue(phoneNumber, text)
}