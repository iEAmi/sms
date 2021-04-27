package me.ahmad.sms.domain

import me.ahmad.sms.domain.exception.SmsException

inline class PhoneNumber internal constructor(val value: String) {
    companion object {
        fun fromString(s: String?): PhoneNumber = s.toPhoneNumber()
    }
}

fun String?.toPhoneNumber(): PhoneNumber {
    if (this == null) throw SmsException.nullArg("null is not a valid phoneNumber.")
    if (this.isBlank()) throw SmsException.invalidArg("empty is not a valid phoneNumber.")

    val regex = Regex("^(09[0-9][0-9]{8})$")

    if (!regex.matches(this)) throw SmsException.invalidArg("$this is not a valid phoneNumber.")

    return PhoneNumber(this)
}