package me.ahmad.sms.infra.queue

import me.ahmad.sms.app.queue.QueueContext
import me.ahmad.sms.domain.Sms
import me.ahmad.sms.domain.service.QueueService

internal class QueueContextImpl(
    private val queueService: QueueService,
    override val sms: Sms,
) : QueueContext {
    override fun done() {
        // nothing
    }

    override fun reject() {
        // nothing
    }

    override fun requeue(sms: Sms) {
        queueService.push(sms)
    }
}