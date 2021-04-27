package me.ahmad.sms

import me.ahmad.sms.domain.common.SmsException
import me.ahmad.sms.domain.common.SmsException.InvalidArgument

class SmsFactory internal constructor(
    private val repo: SmsRepository,
    private val receiverFactory: ReceiverFactory
) {

    @Throws(InvalidArgument::class)
    fun createSms(input: Input): Sms {
        if (input.text.isBlank()) throw SmsException.invalidArg("${input.text} could not be empty.")

        val sms = Sms(
            id = Sms.Id.ZERO,
            receiver = receiverFactory.create(input.receiver),
            text = input.text,
            provider = input.provider,
            status = Sms.Status.Created
        )

        repo.save(sms)

        return sms
    }

    interface Input {
        val receiver: PhoneNumber
        val text: String
        val provider: Provider
    }
}