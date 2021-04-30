package me.ahmad.sms.app.event

import me.ahmad.sms.domain.Provider
import me.ahmad.sms.domain.ProviderRepository
import me.ahmad.sms.domain.Sms
import me.ahmad.sms.domain.event.Event
import org.slf4j.Logger

internal class DefaultEventListener(
    private val logger: Logger,
    private val providerRepository: ProviderRepository
) : EventListener {

    override fun onEvent(event: Event<*>) {
        if (event is Sms.Event) {
            handleSmsEvents(event)

            return
        }

        if (event is Provider.Event) {
            handleProviderEvents(event)

            return
        }
    }

    private fun handleSmsEvents(event: Sms.Event) {
        logSmsEvent(event)

        // todo : save event to database
    }

    private fun handleProviderEvents(event: Provider.Event) {
        logProviderEvent(event)

        val provider = event.entity

        when (event) {
            is Provider.Event.SmsSendFailed -> provider.increaseFailedCountByOne()(providerRepository)
            is Provider.Event.SmsSendSuccessed -> provider.increaseDoneCountByOne()(providerRepository)
        }
    }

    private fun logSmsEvent(event: Sms.Event) {
        logger.info(String.format("%-8s %-21s : %s", "Sms", event.javaClass.simpleName, event.entity))
    }

    private fun logProviderEvent(event: Provider.Event) {
        logger.info(String.format("%-8s %-21s : %s", "Provider", event.javaClass.simpleName, event.entity))
    }
}