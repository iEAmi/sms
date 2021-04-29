package me.ahmad.sms.domain

import me.ahmad.sms.domain.event.AbstractEvent
import me.ahmad.sms.domain.event.EventPublisher


data class Provider(
    override val id: Id,
    val address: Address,
    val totalCount: ULong,
    val doneCount: ULong,
    val donePercent: Double,
    val failedCount: ULong,
    val failedPercent: Double
) : Entity<Provider.Id> {

    fun increaseDoneCountByOne(): (ProviderRepository) -> Provider = {
        var u = this.copy(doneCount = this.doneCount + 1u, totalCount = this.totalCount + 1u)
        u = u.copy(
            donePercent = (u.doneCount.toDouble() / u.totalCount.toDouble()) * 100.0,
            failedPercent = u.failedCount.toDouble() / u.totalCount.toDouble() * 100.0
        )

        it.update(u)

        u
    }

    fun increaseFailedCountByOne(): (ProviderRepository) -> Provider = {
        var u = this.copy(failedCount = this.failedCount + 1u, totalCount = this.totalCount + 1u)
        u = u.copy(
            donePercent = (u.doneCount.toDouble() / u.totalCount.toDouble()) * 100.0,
            failedPercent = u.failedCount.toDouble() / u.totalCount.toDouble() * 100.0
        )

        it.update(u)

        u
    }

    override fun toString(): String {
        return "Provider(" +
                "id=$id, " +
                "address=$address, " +
                "totalCount=$totalCount, " +
                "doneCount=$doneCount, " +
                "donePercent=$donePercent, " +
                "failedCount=$failedCount, " +
                "failedPercent=$failedPercent)"
    }

    sealed class Event : AbstractEvent<Provider>() {
        /**
         * Throws when sms successfully send
         */
        data class SmsSendSuccessed(override val entity: Provider) : Event()

        /**
         * Throws when sms failed
         */
        data class SmsSendFailed(override val entity: Provider) : Event()
    }

    inline class Id(val value: String)
    inline class Address(val value: String)
}

fun Provider.publishEvent(init: ProviderEventSelector.() -> Unit): (EventPublisher) -> Unit = {
    val s = ProviderEventSelector(this, it)
    s.init()
}

class ProviderEventSelector internal constructor(
    private val provider: Provider,
    private val eventPublisher: EventPublisher
) {
    fun smsSendSuccessed() = eventPublisher.publish(Provider.Event.SmsSendSuccessed(provider))
    fun smsSendFailed() = eventPublisher.publish(Provider.Event.SmsSendFailed(provider))
}
