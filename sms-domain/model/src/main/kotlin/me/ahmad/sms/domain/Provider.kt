package me.ahmad.sms.domain


class Provider(
    private val id: Id,
    private val address: Address
) {
    inline class Id(val value: String)
    inline class Address(val value: String)
}
