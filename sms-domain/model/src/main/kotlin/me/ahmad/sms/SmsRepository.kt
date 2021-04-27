package me.ahmad.sms

import me.ahmad.sms.domain.common.SmsException.WrappedException

interface SmsRepository {

    @Throws(WrappedException::class)
    fun save(sms: Sms): Sms.Id
}