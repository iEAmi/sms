package me.ahmad.sms.app

import me.ahmad.sms.app.queue.`sms-app-queue-api-module`
import me.ahmad.sms.app.rest.`sms-app-rest-api-module`
import org.kodein.di.DI

val `sms-app-module` = DI.Module("sms-app-module") {
    import(`sms-app-common-module`)
    import(`sms-app-queue-api-module`)
    import(`sms-app-rest-api-module`)
}