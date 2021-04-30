package me.ahmad.sms.domain

import me.ahmad.sms.domain.SmsException.WrappedException

interface SmsRepository {

    @Throws(WrappedException::class)
    fun save(sms: Sms): Sms

    @Throws(WrappedException::class)
    fun update(sms: Sms)
}