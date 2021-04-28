package me.ahmad.sms.app

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val `sms-app-common-module` = DI.Module("sms-app:common-module") {
    bind<SmsFacadeService>() with singleton { SmsFacadeService(instance()) }
}