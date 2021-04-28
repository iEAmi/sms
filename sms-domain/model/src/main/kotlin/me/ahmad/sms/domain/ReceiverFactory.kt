package me.ahmad.sms.domain

import me.ahmad.sms.domain.SmsException.WrappedException

internal class ReceiverFactory(private val repo: ReceiverRepository) {

    @Throws(WrappedException::class)
    fun create(phoneNumber: PhoneNumber): Receiver =
        repo.get(byPhoneNumber = phoneNumber) ?: createNewReceiver(phoneNumber)

    private fun createNewReceiver(phoneNumber: PhoneNumber): Receiver {
        val receiver = Receiver(id = Receiver.Id.ZERO, phoneNumber)

        repo.save(receiver)

        return receiver
    }
}