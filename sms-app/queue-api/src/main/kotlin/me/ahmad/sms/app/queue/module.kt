package me.ahmad.sms.app.queue

import org.kodein.di.*

val `sms-app-queue-api-module` = DI.Module("sms-app:queue-api-module") {
    bind { provider { SmsHandler(instance(arg = "SmsHandler")) } }
    bind { provider { SmsQueueConsumer(instance(), instance(arg = "SmsQueueConsumer")) } }
    bind { singleton { QueueStarter(instance(), instance()) } }
}