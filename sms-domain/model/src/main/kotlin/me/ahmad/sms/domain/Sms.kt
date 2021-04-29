package me.ahmad.sms.domain

data class Sms(
    val id: Id,
    val receiver: Receiver,
    val text: String,
    val status: Status
) {
    fun goToSendingState(): (SmsRepository) -> Sms = {
        val new = this.copy(status = Status.Sending)
        it.save(new)

        new
    }

    fun goToDoneState(): (SmsRepository) -> Sms = {
        val new = this.copy(status = Status.Done)
        it.save(new)

        new
    }

    fun goToFailedState(): (SmsRepository) -> Sms = {
        val new = this.copy(status = Status.Failed)
        it.save(new)

        new
    }

    fun isFailed(): Boolean = status is Status.Failed

    fun isDone(): Boolean = status is Status.Done

    fun isDoneOrFailed(): Boolean = isDone() || isFailed()

    override fun toString() =
        "Sms(" +
                "id=$id, " +
                "receiver=${receiver.phoneNumber.value}, " +
                "text='$text', " +
                "status=${status.javaClass.simpleName})"

    inline class Id(val value: Long) {
        companion object {
            val ZERO = Id(0)
        }
    }

    sealed class Status {
        object Queued : Status()
        object Sending : Status()
        object Done : Status()
        object Failed : Status()
    }
}