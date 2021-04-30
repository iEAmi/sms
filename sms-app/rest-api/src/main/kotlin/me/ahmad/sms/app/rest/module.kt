package me.ahmad.sms.app.rest

import com.fasterxml.jackson.databind.ObjectMapper
import me.ahmad.sms.app.rest.controller.ReportService
import me.ahmad.sms.app.rest.controller.SmsController
import org.kodein.di.*

val `sms-app-rest-api-module` = DI.Module("sms-app:rest-api-module") {
    bind { provider { SmsController(instance(), instance()) } }
    bind {
        singleton {
            HttpServer(
                instance(arg = "HttpServer"),
                instance(),
                instance(),
                instance("HttpServerConfig")
            )
        }
    }
    bind { provider { ReportService(instance(), instance()) } }
    bind { provider { ObjectMapper() } }
    bind { singleton { SmsExceptionHandler(instance()) } }
}