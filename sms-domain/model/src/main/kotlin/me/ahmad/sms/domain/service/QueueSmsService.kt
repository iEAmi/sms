package me.ahmad.sms.domain.service

import me.ahmad.sms.domain.*
import me.ahmad.sms.domain.ReceiverFactory
import me.ahmad.sms.domain.SmsException.WrappedException
import me.ahmad.sms.domain.SmsFactory
import me.ahmad.sms.domain.event.EventPublisher

class QueueSmsService internal constructor(
    private val receiverFactory: ReceiverFactory,
    private val smsFactory: SmsFactory,
    private val queueService: QueueService,
    private val eventPublisher: EventPublisher
) {
    @Throws(WrappedException::class)
    fun queue(phoneNumber: PhoneNumber, text: String) {
        val receiver = receiverFactory.create(phoneNumber)

        val sms = smsFactory.createSms(object : SmsFactory.Input {
            override val receiver: Receiver = receiver
            override val text: String = text
        })

        queueService.push(sms)

        // publish sms published event
        sms.publishEvent { published() }(eventPublisher)
    }
}