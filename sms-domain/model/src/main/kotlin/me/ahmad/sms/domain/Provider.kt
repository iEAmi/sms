package me.ahmad.sms.domain


class Provider(
    val id: Id,
    val address: Address
) {
    inline class Id(val value: String)
    inline class Address(val value: String)
}
