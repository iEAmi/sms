package me.ahmad.sms.app.queue

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import org.slf4j.Logger
import java.lang.Exception

internal class SmsQueueConsumer(
    private val handler: SmsHandler,
    private val logger: Logger
) : Subscriber<QueueContext> {
    override fun onSubscribe(s: Subscription?) {
        s?.request(Long.MAX_VALUE)
    }

    override fun onNext(t: QueueContext) {
        try {
            handler.handle(t)
        } catch (e: Exception) {
            e.printStackTrace()
            // TODO : publish sms exception event
        }
    }

    override fun onError(t: Throwable?) {
        logger.error("Error occurred in Queue flow", t)
    }

    override fun onComplete() {
        logger.info("Queue flow completed.")
    }
}