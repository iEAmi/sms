package me.ahmad.sms.infra.persistence.table

import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.sortedByDescending
import org.ktorm.schema.Table
import org.ktorm.schema.datetime
import org.ktorm.schema.long
import org.ktorm.schema.text

object Events : Table<Nothing>("events") {
    val id = long("id").primaryKey()
    val smsId = long("sms_id")
    val payload = text("payload")
    val creationDate = datetime("creation_date")
}

val Database.events get() = this.sequenceOf(Events).sortedByDescending { it.creationDate }