package me.ahmad.sms.domain

import me.ahmad.sms.domain.SmsException.InvalidArgument

internal class SmsFactory(
    private val repo: SmsRepository
) {

    @Throws(InvalidArgument::class)
    fun createSms(input: Input): Sms {
        if (input.text.isBlank()) throw SmsException.invalidArg("${input.text} could not be empty.")

        val sms = Sms(
            id = Sms.Id.ZERO,
            receiver = input.receiver,
            text = input.text,
            provider = input.provider,
            status = Sms.Status.Created
        )

        // TODO : publish Sms created event

        repo.save(sms.copy(status = Sms.Status.Saved))

        // TODO : publish Sms saved event

        return sms
    }

    interface Input {
        val receiver: Receiver
        val text: String
        val provider: Provider
    }
}