package me.ahmad.sms.domain

class Receiver(
    val id: Id,
    val phoneNumber: PhoneNumber
) {
    inline class Id(val value: Long) {
        companion object {
            val ZERO = Id(0)
        }
    }
}