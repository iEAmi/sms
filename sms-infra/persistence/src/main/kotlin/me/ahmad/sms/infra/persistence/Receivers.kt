package me.ahmad.sms.infra.persistence

import me.ahmad.sms.domain.PhoneNumber
import me.ahmad.sms.domain.Receiver
import org.ktorm.database.Database
import org.ktorm.dsl.QueryRowSet
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.long
import org.ktorm.schema.varchar

internal object Receivers : RichTable<Receiver>("receivers") {
    val id = long("id").primaryKey().transform(Receiver::Id) { it.value }
    val phoneNumber = varchar("phone_number").transform(PhoneNumber.Companion::fromString) { it.value }

    override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean) = Receiver(
        id = row.column(id),
        phoneNumber = row.column(phoneNumber)
    )
}

internal val Database.receivers get() = this.sequenceOf(Receivers)