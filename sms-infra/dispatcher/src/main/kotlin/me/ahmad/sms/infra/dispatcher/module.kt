package me.ahmad.sms.infra.dispatcher

import me.ahmad.sms.app.queue.SmsDispatcher
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val `sms-infra-dispatcher-module` = DI.Module("sms-infra-dispatcher-module") {
    bind<SmsDispatcher> { singleton { SmsDispatcherImpl() } }
}