package me.ahmad.sms.infra.queue

import io.reactivex.rxjava3.processors.PublishProcessor
import me.ahmad.sms.domain.Entity
import me.ahmad.sms.domain.event.Event
import me.ahmad.sms.domain.event.EventPublisher

internal class EventPublisherImpl : EventPublisher {
    private val flow = PublishProcessor.create<Event<*>>()

    override fun <T : Entity<*>> publish(event: Event<T>) = flow.onNext(event)
}