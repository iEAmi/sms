package me.ahmad.sms.app

import me.ahmad.sms.domain.PhoneNumber
import me.ahmad.sms.domain.SmsException.WrappedException
import me.ahmad.sms.domain.service.SendSmsService
import me.ahmad.sms.domain.service.SendSmsService.ProviderNotFoundException


class SmsFacadeService internal constructor(
    private val sendSmsService: SendSmsService
) {

    @Throws(WrappedException::class, ProviderNotFoundException::class)
    fun send(phoneNumber: PhoneNumber, text: String) {
        sendSmsService.send(phoneNumber, text)
    }
}