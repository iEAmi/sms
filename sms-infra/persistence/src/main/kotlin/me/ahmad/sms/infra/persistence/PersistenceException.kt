package me.ahmad.sms.infra.persistence

import me.ahmad.sms.domain.SmsException

class PersistenceException internal constructor(title: String) :
    SmsException.WrappedException("ahmad://Sms/Wrapped/PersistenceException", title)