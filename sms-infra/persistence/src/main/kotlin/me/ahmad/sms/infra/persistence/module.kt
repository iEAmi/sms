package me.ahmad.sms.infra.persistence

import me.ahmad.sms.app.event.EventWarehouse
import me.ahmad.sms.infra.persistence.repository.`repository-module`
import me.ahmad.sms.infra.persistence.table.`table-module`
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val `sms-infra-persistence-module` = DI.Module("sms-infra-persistence-module") {
    bind<EventWarehouse> { singleton { EventWarehouseImpl(instance()) } }
    import(`repository-module`)
    import(`table-module`)
}