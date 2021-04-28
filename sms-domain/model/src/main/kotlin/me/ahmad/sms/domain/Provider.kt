package me.ahmad.sms.domain

class Provider(
    private val id: Id,
    private val address: Address
) {
    inline class Id(val value: Long)
    inline class Address(val value: String)
}