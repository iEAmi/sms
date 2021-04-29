package me.ahmad.sms.domain

import me.ahmad.sms.domain.service.ProviderSelector
import me.ahmad.sms.domain.service.QueueSmsService
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val `sms-domain-model-module` = DI.Module("sms-domain:model-module") {
    bind { provider { ReceiverFactory(instance()) } }
    bind { provider { SmsFactory(instance(), instance()) } }
    bind { provider { ProviderSelector(instance()) } }
    bind { provider { QueueSmsService(instance(), instance(), instance(), instance()) } }
}