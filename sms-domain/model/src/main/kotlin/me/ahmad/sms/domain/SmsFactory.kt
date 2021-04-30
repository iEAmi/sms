package me.ahmad.sms.domain

import me.ahmad.sms.domain.SmsException.InvalidArgument
import me.ahmad.sms.domain.event.EventPublisher

internal class SmsFactory(
    private val repo: SmsRepository,
    private val eventPublisher: EventPublisher
) {

    @Throws(InvalidArgument::class)
    fun createSms(input: Input): Sms {
        if (input.text.isBlank()) throw SmsException.invalidArg("${input.text} could not be empty.")

        val sms = repo.save(
            Sms(
                id = Sms.Id.ZERO,
                receiver = input.receiver,
                text = input.text,
                status = Sms.Status.Queued
            )
        )

        // publish Sms saved event
        sms.publishEvent { saved() }(eventPublisher)

        return sms
    }

    interface Input {
        val receiver: Receiver
        val text: String
    }
}