package me.ahmad.sms.app.queue

import org.kodein.di.*

val `sms-app-queue-api-module` = DI.Module("sms-app:queue-api-module") {
    bind { provider { SmsHandler(instance(), instance(), instance(), instance()) } }
    bind<QueueConsumer> { provider { DefaultQueueConsumer(instance(), instance()) } }
    bind { singleton { QueueStarter(instance(), instance()) } }
}