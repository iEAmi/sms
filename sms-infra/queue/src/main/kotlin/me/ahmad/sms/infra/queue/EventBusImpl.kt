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
    private val flow = PublishProcessor.create<Event<*>>()
    private var listener: EventListener? = null

    init {
        flow.share()
            .onBackpressureBuffer()
            .distinct { it.id }
            .subscribeOn(Schedulers.computation())
            .subscribe(this)
    }

    override fun <T : Entity<*>> publish(event: Event<T>) = flow.onNext(event)

    override fun setListener(listener: EventListener) {
        this.listener = listener
    }

    override fun close() {
        flow.onComplete()
    }

    override fun onSubscribe(s: Subscription?) {
        s?.request(Long.MAX_VALUE)
    }

    override fun onNext(t: Event<*>) {
        listener?.onEvent(t)
    }

    override fun onError(t: Throwable?) {
        t?.printStackTrace()
    }

    override fun onComplete() {

    }
}