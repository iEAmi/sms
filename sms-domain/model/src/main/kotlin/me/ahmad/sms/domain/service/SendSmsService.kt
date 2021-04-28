package me.ahmad.sms.domain.service

import me.ahmad.sms.domain.*
import me.ahmad.sms.domain.SmsException
import me.ahmad.sms.domain.SmsException.WrappedException

class SendSmsService internal constructor(
    private val providerSelector: ProviderSelector,
    private val receiverFactory: ReceiverFactory,
    private val smsFactory: SmsFactory
) {
    @Throws(WrappedException::class, ProviderNotFoundException::class)
    fun send(phoneNumber: PhoneNumber, text: String) {
        val receiver = receiverFactory.create(phoneNumber)
        val provider = providerSelector.select(receiver, text)
            ?: throw ProviderNotFoundException("Could not find any provider. try again later")

        val sms = smsFactory.createSms(object : SmsFactory.Input {
            override val receiver: Receiver = receiver
            override val text: String = text
            override val provider: Provider = provider
        })


    }

    class ProviderNotFoundException(
        override val title: String
    ) : SmsException("ahmad://Sms/ProviderNotFoundException", title)
}