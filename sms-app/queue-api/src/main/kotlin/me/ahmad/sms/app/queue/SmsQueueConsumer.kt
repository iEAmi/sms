package me.ahmad.sms.app.queue

import me.ahmad.sms.domain.Sms
import me.ahmad.sms.domain.event.EventPublisher
import me.ahmad.sms.domain.publishEvent
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import org.slf4j.Logger
import java.lang.Exception

internal class SmsQueueConsumer(
    private val handler: SmsHandler,
    private val logger: Logger,
    private val eventPublisher: EventPublisher
) : Subscriber<QueueContext> {
    override fun onSubscribe(s: Subscription?) {
        s?.request(Long.MAX_VALUE)
    }

    override fun onNext(t: QueueContext) {
        var sms = t.sms.copy()

        try {
            sms = handler.handle(t)
        } catch (e: Exception) {
            e.printStackTrace()

            // publish sms UnHandleExceptionThrew  event
            sms.publishEvent { unhandledExceptionThrew(e) }(eventPublisher)
        }
    }

    override fun onError(t: Throwable?) {
        logger.error("Error occurred in Queue flow", t)
    }

    override fun onComplete() {
        logger.info("Queue flow completed.")
    }
}