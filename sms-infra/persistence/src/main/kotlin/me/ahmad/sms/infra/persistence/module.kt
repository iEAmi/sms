package me.ahmad.sms.infra.persistence

import me.ahmad.sms.infra.persistence.repository.`repository-module`
import me.ahmad.sms.infra.persistence.table.`table-module`
import org.kodein.di.DI

val `sms-infra-persistence-module` = DI.Module("sms-infra-persistence-module") {
    import(`repository-module`)
    import(`table-module`)
}