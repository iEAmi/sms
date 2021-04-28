package me.ahmad.sms.domain

class Sms internal constructor(
    private val id: Id,
    private val receiver: Receiver,
    private val text: String,
    private val provider: Provider,
    private val status: Status
) {
    inline class Id(val value: Long) {
        companion object {
            val ZERO = Id(0)
        }
    }

    sealed class Status {
        data class Queued(private val retry: Int, private val maxRetry: Int) : Status()

        object Created : Status()
        object Saved : Status()
        object Consumed : Status()
        object Sending : Status()
        object Done : Status()
        object Failed : Status()
    }
}