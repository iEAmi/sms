package me.ahmad.sms.domain.event

import me.ahmad.sms.domain.Entity

interface EventPublisher {
    fun <T : Entity<*>> publish(event: Event<T>)
}