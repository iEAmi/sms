package me.ahmad.sms.domain.service

import me.ahmad.sms.domain.*
import me.ahmad.sms.domain.SmsException
import me.ahmad.sms.domain.SmsException.WrappedException

class QueueSmsService internal constructor(
    private val providerSelector: ProviderSelector,
    private val receiverFactory: ReceiverFactory,
    private val smsFactory: SmsFactory,
    private val queueService: QueueService
) {
    @Throws(WrappedException::class, ProviderNotFoundException::class)
    fun queue(phoneNumber: PhoneNumber, text: String) {
        val receiver = receiverFactory.create(phoneNumber)
        val provider = providerSelector.select(receiver, text)
            ?: throw ProviderNotFoundException("Could not find any provider. try again later")

        val sms = smsFactory.createSms(object : SmsFactory.Input {
            override val receiver: Receiver = receiver
            override val text: String = text
            override val provider: Provider = provider
        })

        queueService.push(sms)

        // todo : publish sms published event
    }

    class ProviderNotFoundException(
        override val title: String
    ) : SmsException("ahmad://Sms/ProviderNotFoundException", title)
}