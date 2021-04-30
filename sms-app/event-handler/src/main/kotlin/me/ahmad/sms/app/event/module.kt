package me.ahmad.sms.app.event

import com.google.gson.GsonBuilder
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val `sms-app-event-module` = DI.Module("sms-app-event-module") {
    bind { provider { GsonBuilder().create() } }
    bind<EventListener> { provider { DefaultEventListener(instance(arg = "DefaultEventListener"), instance()) } }
    bind { provider { EventWebSocketServer(instance(arg = "EventWebSocketServer"), instance()) } }
    bind { provider { EventListenerStarter(instance(), instance(), instance()) } }
}