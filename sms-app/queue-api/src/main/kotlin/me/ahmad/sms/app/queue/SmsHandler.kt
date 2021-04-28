package me.ahmad.sms.app.queue

import org.slf4j.Logger

internal class SmsHandler(
    private val logger: Logger
) {

    fun handle(ctx: QueueContext) {
        logger.info("${ctx.sms} consumed.")

    }
}