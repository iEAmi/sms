package me.ahmad.sms.domain

data class Sms(
    val id: Id,
    val receiver: Receiver,
    val text: String,
    val provider: Provider,
    val status: Status
) {
    override fun toString() =
        "Sms(" +
                "id=$id, " +
                "receiver=${receiver.phoneNumber.value}, " +
                "text='$text', " +
                "provider=${provider.id}, " +
                "status=${status.javaClass.simpleName})"

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