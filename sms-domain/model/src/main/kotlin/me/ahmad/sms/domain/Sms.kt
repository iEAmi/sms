package me.ahmad.sms.domain

import me.ahmad.sms.domain.event.AbstractEvent
import me.ahmad.sms.domain.event.EventPublisher

data class Sms(
    override val id: Id,
    val receiver: Receiver,
    val text: String,
    val status: Status
) : Entity<Sms.Id> {
    fun goToSendingState(): (SmsRepository) -> Sms = {
        it.save(this.copy(status = Status.Sending))
    }

    fun goToDoneState(): (SmsRepository) -> Sms = {
        it.save(this.copy(status = Status.Done))
    }

    fun goToFailedState(): (SmsRepository) -> Sms = {
        it.save(this.copy(status = Status.Failed))
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

    sealed class Event : AbstractEvent<Sms>() {
        /**
         * Throws when new Sms object was saved in database.
         */
        data class Saved(override val entity: Sms) : Event()

        /**
         * Throws when new Sms object was published in queue.
         */
        data class Published(override val entity: Sms) : Event()

        /**
         * Throws when an critical exception have been threw in sms processing.
         */
        data class UnhandledExceptionThrew(override val entity: Sms, val t: Throwable) : Event()

        /**
         * Throws when a consumer consumed sms.
         */
        data class Consumed(override val entity: Sms) : Event()

        /**
         * Throws when sms was sent successfully.
         */
        data class Done(override val entity: Sms, val provider: Provider) : Event()

        /**
         * Throws when failed to sent sms with all available providers.
         */
        data class Failed(override val entity: Sms) : Event()

        /**
         * Throws when sms was published to retry queue.
         */
        data class PublishedToRetryQueue(override val entity: Sms) : Event()
    }

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

fun Sms.publishEvent(init: SmsEventSelector.() -> Unit): (EventPublisher) -> Unit = {
    val s = SmsEventSelector(this, it)
    s.init()
}

class SmsEventSelector internal constructor(
    private val sms: Sms,
    private val eventPublisher: EventPublisher
) {
    fun saved() = eventPublisher.publish(Sms.Event.Saved(sms))
    fun published() = eventPublisher.publish(Sms.Event.Published(sms))
    fun unhandledExceptionThrew(t: Throwable) = eventPublisher.publish(Sms.Event.UnhandledExceptionThrew(sms, t))
    fun consumed() = eventPublisher.publish(Sms.Event.Consumed(sms))
    fun done(provider: Provider) = eventPublisher.publish(Sms.Event.Done(sms, provider))
    fun failed() = eventPublisher.publish(Sms.Event.Failed(sms))
    fun publishedToRetryQueue() = eventPublisher.publish(Sms.Event.PublishedToRetryQueue(sms))
}
