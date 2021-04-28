package me.ahmad.sms.domain

import me.ahmad.sms.domain.service.ProviderSelector
import me.ahmad.sms.domain.service.QueueSmsService
import org.kodein.di.*

val `sms-domain-model-module` = DI.Module("sms-domain:model-module") {
    bind { provider { ReceiverFactory(instance()) } }
    bind { provider { SmsFactory(instance()) } }
    bind { provider { ProviderSelector(instance()) } }
    bind { provider { QueueSmsService(instance(), instance(), instance(), instance()) } }
}