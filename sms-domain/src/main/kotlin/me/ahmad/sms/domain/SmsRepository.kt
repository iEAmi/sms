package me.ahmad.sms.domain

import me.ahmad.sms.domain.exception.SmsException.WrappedException

interface SmsRepository {

    @Throws(WrappedException::class)
    fun save(sms: Sms): Sms.Id
}