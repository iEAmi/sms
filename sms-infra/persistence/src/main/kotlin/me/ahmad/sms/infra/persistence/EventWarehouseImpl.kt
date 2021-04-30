package me.ahmad.sms.infra.persistence

import me.ahmad.sms.app.event.EventWarehouse
import me.ahmad.sms.domain.Sms
import me.ahmad.sms.infra.persistence.table.Events
import org.ktorm.database.Database
import org.ktorm.dsl.insert

internal class EventWarehouseImpl(private val database: Database) : EventWarehouse {

    override fun store(event: Sms.Event, payload: String) {
        database.insert(Events) {
            set(it.id, event.id)
            set(it.smsId, event.entity.id)
            set(it.payload, payload)
            set(it.creationDate, event.creationDate)
        }
    }
}