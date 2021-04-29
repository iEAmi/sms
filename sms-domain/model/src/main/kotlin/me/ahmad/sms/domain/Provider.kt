package me.ahmad.sms.domain

import me.ahmad.sms.domain.event.AbstractEvent
import me.ahmad.sms.domain.event.EventPublisher


class Provider(
    override val id: Id,
    val address: Address
) : Entity<Provider.Id> {

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
