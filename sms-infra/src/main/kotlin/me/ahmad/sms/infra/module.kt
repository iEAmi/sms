package me.ahmad.sms.infra

import me.ahmad.sms.infra.dispatcher.`sms-infra-dispatcher-module`
import me.ahmad.sms.infra.persistence.`sms-infra-persistence-module`
import me.ahmad.sms.infra.queue.`sms-infra-queue-module`
import org.kodein.di.DI

val `sms-infra-module` = DI.Module("sms-infra-module") {
    import(`sms-infra-persistence-module`)
    import(`sms-infra-queue-module`)
    import(`sms-infra-dispatcher-module`)
}