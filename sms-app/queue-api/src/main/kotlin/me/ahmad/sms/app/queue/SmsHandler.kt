package me.ahmad.sms.app.queue

import me.ahmad.sms.domain.Provider
import me.ahmad.sms.domain.Sms
import me.ahmad.sms.domain.SmsRepository
import me.ahmad.sms.domain.event.EventPublisher
import me.ahmad.sms.domain.publishEvent
import me.ahmad.sms.domain.service.ProviderSelector

internal class SmsHandler(
    private val dispatcher: SmsDispatcher,
    private val smsRepository: SmsRepository,
    private val providerSelector: ProviderSelector,
    private val eventPublisher: EventPublisher
) {
    fun handle(ctx: QueueContext): Sms {
        var sms = ctx.sms
        sms.publishEvent { consumed() }(eventPublisher)

        if (sms.isDoneOrFailed()) {
            // reject sms because this was done/failed
            ctx.reject()
            return sms
        }

        val providers = providerSelector.select(sms.receiver, sms.text)
        if (providers.isEmpty()) {
            // no provider exist, so requeue sms until provider available
            ctx.requeue(sms)
            return sms
        }

        // Changes Sms state to SENDING
        sms = sms.goToSendingState()(smsRepository)

        // iterates providers and tries send sms
        val handledSms = doHandle(sms, providers)

        // finally, drop SMS from queue
        ctx.done()

        return handledSms
    }

    private fun doHandle(sms: Sms, providers: List<Provider>): Sms {
        var smsCopy = sms.copy()

        for (provider in providers) {
            // tries to send sms with all available providers.
            smsCopy = execute(smsCopy, provider)

            // if sms was done then break for loop and ignore remaining providers
            if (smsCopy.isDone())
                return smsCopy
        }

        smsCopy = smsCopy.goToFailedState()(smsRepository)

        // publish sms Failed event and retry queue
        smsCopy.publishEvent { failed(); publishedToRetryQueue() }(eventPublisher)

        return smsCopy
    }

    private fun execute(sms: Sms, provider: Provider): Sms {
        val result = dispatcher.dispatch(sms, provider)

        return if (result) {
            provider.publishEvent { smsSendSuccessed() }(eventPublisher)

            val temp = sms.goToDoneState()(smsRepository)

            temp.publishEvent { done(provider) }(eventPublisher)

            temp
        } else {
            provider.publishEvent { smsSendFailed() }(eventPublisher)

            sms
        }
    }
}