package me.ahmad.sms.domain

import me.ahmad.sms.domain.SmsException.WrappedException

interface ReceiverRepository {
    @Throws(WrappedException::class)
    fun get(byPhoneNumber: PhoneNumber): Receiver?

    @Throws(WrappedException::class)
    fun getTopTen(): List<Receiver>

    @Throws(WrappedException::class)
    fun save(receiver: Receiver): Receiver
}