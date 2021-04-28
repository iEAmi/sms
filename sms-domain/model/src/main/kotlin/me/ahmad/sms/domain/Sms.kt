package me.ahmad.sms.domain

data class Sms(
    private val id: Id,
    val receiver: Receiver,
    val text: String,
    val provider: Provider,
    val status: Status
) {
    inline class Id(val value: Long) {
        companion object {
            val ZERO = Id(0)
        }
    }

    sealed class Status {
        data class Queued(val retry: Int) : Status()

        object Created : Status()
        object Saved : Status()
        object Consumed : Status()
        object Sending : Status()
        object Done : Status()
        object Failed : Status()
    }
}