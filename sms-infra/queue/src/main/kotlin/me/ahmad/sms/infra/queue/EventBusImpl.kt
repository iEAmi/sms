package me.ahmad.sms.infra.queue

import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.schedulers.Schedulers
import me.ahmad.sms.app.event.EventBus
import me.ahmad.sms.app.event.EventListener
import me.ahmad.sms.domain.Entity
import me.ahmad.sms.domain.event.Event
import me.ahmad.sms.domain.event.EventPublisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

internal class EventBusImpl : EventBus, EventPublisher, Subscriber<Event<*>> {
    private val subject = PublishProcessor.create<Event<*>>()
    private val flow = subject.onBackpressureBuffer()
        .distinct { it.id }
        .observeOn(Schedulers.computation())
    private val listeners = HashSet<EventListener>()
    private lateinit var subscription: Subscription

    init {
        flow.subscribe(this)
    }

    override fun <T : Entity<*>> publish(event: Event<T>) = subject.onNext(event)

    override fun registerListener(listener: EventListener) {
        this.listeners.add(listener)
    }

    override fun close() {
        subscription.cancel()
        subject.onComplete()
    }

    override fun onSubscribe(s: Subscription) {
        this.subscription = s
        s.request(1)
    }

    override fun onNext(t: Event<*>) {
        listeners.forEach { it.onEvent(t) }

        subscription.request(1)
    }

    override fun onError(t: Throwable?) {
        t?.printStackTrace()
    }

    override fun onComplete() {

    }
}