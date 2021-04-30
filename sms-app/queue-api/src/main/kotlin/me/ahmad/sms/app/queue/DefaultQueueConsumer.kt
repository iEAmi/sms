package me.ahmad.sms.app.queue

import me.ahmad.sms.domain.event.EventPublisher
import me.ahmad.sms.domain.publishEvent

internal class DefaultQueueConsumer(
    private val handler: SmsHandler,
    private val eventPublisher: EventPublisher
) : QueueConsumer {
    override fun onEvent(event: QueueContext) {
        var sms = event.sms.copy()

        try {
            sms = handler.handle(event)
        } catch (e: Exception) {
            e.printStackTrace()

            // publish sms UnHandleExceptionThrew  event
            sms.publishEvent { unhandledExceptionThrew(e) }(eventPublisher)
        }
    }
}