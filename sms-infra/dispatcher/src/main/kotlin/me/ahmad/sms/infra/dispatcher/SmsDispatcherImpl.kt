package me.ahmad.sms.infra.dispatcher

import me.ahmad.sms.app.queue.SmsDispatcher
import me.ahmad.sms.domain.Provider
import me.ahmad.sms.domain.Sms
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ThreadLocalRandom

internal class SmsDispatcherImpl : SmsDispatcher {
    private val map = ConcurrentHashMap<Provider.Id, Boolean>()
    private val rng = ThreadLocalRandom.current()

    override fun dispatch(sms: Sms, provider: Provider): Boolean =
        map.computeIfAbsent(provider.id) { rng.nextBoolean() }.not()
}