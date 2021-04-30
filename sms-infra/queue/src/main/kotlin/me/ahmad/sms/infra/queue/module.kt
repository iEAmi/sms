package me.ahmad.sms.infra.queue

import me.ahmad.sms.app.event.EventBus
import me.ahmad.sms.app.queue.Queue
import me.ahmad.sms.domain.event.EventPublisher
import me.ahmad.sms.domain.service.QueueService
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val `sms-infra-queue-module` = DI.Module("sms-infra-queue-module") {
    bind<Queue> { singleton { InMemoryQueue() } }
    bind<QueueService> { singleton { InMemoryQueue() } }
    bind<EventBus> { singleton { EventBusImpl() } }
    bind<EventPublisher> { singleton { EventBusImpl() } }
}