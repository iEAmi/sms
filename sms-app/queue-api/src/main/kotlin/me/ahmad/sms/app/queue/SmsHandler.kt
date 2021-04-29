package me.ahmad.sms.app.queue

import me.ahmad.sms.domain.Sms
import me.ahmad.sms.domain.SmsRepository
import me.ahmad.sms.domain.service.ProviderSelector
import org.slf4j.Logger

internal class SmsHandler(
    private val logger: Logger,
    private val dispatcher: SmsDispatcher,
    private val smsRepository: SmsRepository,
    private val providerSelector: ProviderSelector
) {

    fun handle(ctx: QueueContext) {
        logger.info("${ctx.sms} consumed.")
        // TODO : publish sms consumed event

        val sms = ctx.sms
        if (sms.isDoneOrFailed()) {
            ctx.reject()
            return
        }

        val result = dispatcher.dispatch(sms)

        if (result) smsDone(ctx) else smsFailed(ctx)
    }

    private fun smsDone(ctx: QueueContext) {
        val sms = ctx.sms.copy(status = Sms.Status.Done)

        try {
            smsRepository.update(sms)
        } catch (e: Exception) {
            // ignoring exception here. because sms was sent
        } finally {
            ctx.done()

            logger.info("$sms Done.")
            // TODO : publish sms Done event
        }
    }

    private fun smsFailed(ctx: QueueContext) {
        val retry = (ctx.sms.status as Sms.Status.Queued).retry + 1

        val sms = if (retry < 3) {
            // select another provider for retrying
            val newProvider = providerSelector.select(ctx.sms.receiver, ctx.sms.text, ctx.sms.provider)

            ctx.sms.copy(provider = newProvider ?: ctx.sms.provider, status = Sms.Status.Queued(retry))
        } else {
            // sms failed
            ctx.sms.copy(status = Sms.Status.Failed)
        }

        try {
            smsRepository.update(sms)
        } catch (e: Exception) {
            if (sms.isFailed()) {
                // sms failed
                ctx.reject()

                logger.info("$sms failed.")
                // TODO : publish sms failed event
            } else {
                ctx.requeue(sms)

                logger.info("$sms Queue.")
                // TODO : publish sms Queue event
            }
        }
    }
}