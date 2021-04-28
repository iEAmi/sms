package me.ahmad.sms.app.rest

import me.ahmad.sms.app.rest.controller.SmsController
import org.kodein.di.*

val `sms-app-rest-api-module` = DI.Module("sms-app:rest-api-module") {
    bind { provider { SmsController(instance()) } }
    bind { singleton { HttpServer(instance(arg = "HttpServer"), instance(), instance(), instance("HttpServerConfig")) } }
    bind { singleton { SmsExceptionHandler(instance()) } }
}