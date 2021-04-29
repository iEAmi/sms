package me.ahmad.sms.app.queue

import org.kodein.di.*

val `sms-app-queue-api-module` = DI.Module("sms-app:queue-api-module") {
    bind { provider { SmsHandler(instance(arg = "SmsHandler"), instance(), instance(), instance(), instance()) } }
    bind { provider { SmsQueueConsumer(instance(), instance(arg = "SmsQueueConsumer"), instance()) } }
    bind { singleton { QueueStarter(instance(), instance()) } }
}