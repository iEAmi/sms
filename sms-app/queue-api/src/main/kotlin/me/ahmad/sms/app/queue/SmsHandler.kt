package me.ahmad.sms.app.queue

import me.ahmad.sms.domain.Provider
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
            // reject sms because this was done/failed
            ctx.reject()
            return
        }

        // Changes Sms state to SENDING
        sms.goToSendingState().invoke(smsRepository)

        val providers = providerSelector.select(sms.receiver, sms.text)
        if (providers.isEmpty()) {
            // no provider exist, so requeue sms until provider available
            ctx.requeue(sms)
            return
        }

        // iterates providers and tries send sms
        doHandle(ctx, providers)

        // finally, drop SMS from queue
        ctx.done()
    }

    private fun doHandle(ctx: QueueContext, providers: List<Provider>) {
        var sms = ctx.sms.copy()

        for (provider in providers) {
            // tries to send sms with all available providers.
            sms = execute(sms, provider)

            // if sms was done then break for loop and ignore remaining providers
            if (sms.isDone())
                return
        }

        // TODO : publish sms Failed event

        sms = sms.goToFailedState().invoke(smsRepository)
        // TODO : publish SMS to retry queue
    }

    private fun execute(sms: Sms, provider: Provider): Sms {
        val result = dispatcher.dispatch(sms, provider)

        return if (result) {
            // TODO publish sms done event
            // TODO publish provider Done event

            sms.goToDoneState().invoke(smsRepository)
        } else {
            // TODO publish provider failed event

            sms
        }
    }
}