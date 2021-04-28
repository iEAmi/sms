package me.ahmad.sms.infra.queue

import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.schedulers.Schedulers
import me.ahmad.sms.app.queue.Queue
import me.ahmad.sms.app.queue.QueueContext
import me.ahmad.sms.domain.Sms
import me.ahmad.sms.domain.service.QueueService
import org.reactivestreams.Subscriber

internal object InMemoryQueue : Queue(), QueueService {
    private val flow = PublishProcessor.create<Sms>()

    override fun subscribeActual(subscriber: Subscriber<in QueueContext>?) {
        flow.share()
            .onBackpressureBuffer()
            .distinct { it.id }
            .subscribeOn(Schedulers.computation())
            .map { QueueContextImpl(this, it, null) }
            .subscribe(subscriber)
    }

    override fun push(sms: Sms) {
        flow.onNext(sms)
    }

    override fun close() {
        flow.onComplete()
    }
}