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
        logger.info("New event received: ${event.javaClass.simpleName} -> $${event.creationDate} ${event.id}")

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
        when (event) {
            is Sms.Event.Consumed -> logger.info("Sms consumed : ${event.entity}")
            is Sms.Event.Done -> logger.info("Sms Done : ${event.entity}")
            is Sms.Event.Failed -> logger.info("Sms failed : ${event.entity}")
            is Sms.Event.Published -> logger.info("Sms published : ${event.entity}")
            is Sms.Event.PublishedToRetryQueue -> logger.info("Sms PublishedToRetryQueue : ${event.entity}")
            is Sms.Event.Saved -> logger.info("Sms Saved : ${event.entity}")
            is Sms.Event.UnhandledExceptionThrew -> logger.info("Sms UnhandledExceptionThrew : ${event.entity}")
        }

        // todo : save event to database
    }

    private fun handleProviderEvents(event: Provider.Event) {
        val provider = event.entity

        when (event) {
            is Provider.Event.SmsSendFailed -> {
                logger.info("Provider failed : $provider")

                provider.increaseFailedCountByOne()(providerRepository)
            }
            is Provider.Event.SmsSendSuccessed -> {
                logger.info("Provider done : $provider")

                provider.increaseDoneCountByOne()(providerRepository)
            }
        }
    }
}