package me.ahmad.sms.infra.queue

import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.schedulers.Schedulers
import me.ahmad.sms.app.queue.Queue
import me.ahmad.sms.app.queue.QueueConsumer
import me.ahmad.sms.domain.Sms
import me.ahmad.sms.domain.service.QueueService
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

internal class InMemoryQueue : Queue, QueueService, Subscriber<Sms> {
    private val subject = PublishProcessor.create<Sms>()
    private val flow = subject.onBackpressureBuffer().distinct { it.id }.observeOn(Schedulers.computation())
    private var consumer: QueueConsumer? = null
    private lateinit var subscription: Subscription

    init {
        flow.subscribe(this)
    }

    override fun setConsumer(consumer: QueueConsumer) {
        this.consumer = consumer
    }

    override fun push(sms: Sms) {
        subject.onNext(sms)
    }

    override fun close() {
        subject.onComplete()
        this.subscription.cancel()
    }

    override fun onSubscribe(s: Subscription) {
        this.subscription = s
        this.subscription.request(1)
    }

    override fun onNext(t: Sms) {
        this.consumer?.onEvent(QueueContextImpl(this, t))

        this.subscription.request(1)
    }

    override fun onError(t: Throwable?) {
        t?.printStackTrace()
    }

    override fun onComplete() {

    }
}