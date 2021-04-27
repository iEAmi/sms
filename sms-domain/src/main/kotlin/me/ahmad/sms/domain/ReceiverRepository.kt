package me.ahmad.sms.domain

import me.ahmad.sms.domain.exception.SmsException.WrappedException
import kotlin.jvm.Throws

interface ReceiverRepository {
    @Throws(WrappedException::class)
    fun get(byPhoneNumber: PhoneNumber): Receiver?

    @Throws(WrappedException::class)
    fun save(receiver: Receiver): Receiver.Id
}