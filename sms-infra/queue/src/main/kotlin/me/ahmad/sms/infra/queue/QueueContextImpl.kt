package me.ahmad.sms.infra.queue

import me.ahmad.sms.app.queue.QueueContext
import me.ahmad.sms.domain.Sms
import me.ahmad.sms.domain.service.QueueService
import org.slf4j.Logger

internal class QueueContextImpl(
    private val queueService: QueueService,
    override val sms: Sms,
    private val logger: Logger?
) : QueueContext {
    override fun done() {
        logger?.debug("$sms done")
    }

    override fun reject() {
        logger?.debug("$sms rejected")
    }

    override fun requeue() {
        logger?.info("$sms requeue")

        queueService.push(sms)
    }
}