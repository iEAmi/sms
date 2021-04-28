package me.ahmad.sms.domain

class Receiver(
    private val id: Id,
    private val phoneNumber: PhoneNumber
) {
    inline class Id(val value: Long) {
        companion object {
            val ZERO = Id(0)
        }
    }
}