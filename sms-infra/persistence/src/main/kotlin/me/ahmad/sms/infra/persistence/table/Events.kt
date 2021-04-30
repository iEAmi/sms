package me.ahmad.sms.infra.persistence.table

import me.ahmad.sms.domain.Sms
import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.sortedByDescending
import org.ktorm.schema.*

object Events : Table<Nothing>("events") {
    val id = uuid("id").primaryKey()
    val smsId = long("message_id").transform(Sms::Id) { it.value }
    val payload = text("payload")
    val creationDate = datetime("creation_date")
}

val Database.events get() = this.sequenceOf(Events).sortedByDescending { it.creationDate }