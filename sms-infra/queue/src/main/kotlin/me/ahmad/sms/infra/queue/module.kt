package me.ahmad.sms.infra.queue

import me.ahmad.sms.app.queue.Queue
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val `sms-infra-queue-module` = DI.Module("sms-infra-queue-module") {
    bind<Queue> { singleton { InMemoryQueue(instance()) } }
}