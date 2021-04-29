package me.ahmad.sms.infra.persistence.table

import me.ahmad.sms.domain.Provider
import me.ahmad.sms.domain.Receiver
import me.ahmad.sms.domain.Sms
import org.ktorm.database.Database
import org.ktorm.dsl.QueryRowSet
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.*

internal object SMSes : RichTable<Sms>("messages") {
    val id = long("id").primaryKey().transform(Sms::Id) { it.value }
    val receiverId = long("receiver_id").transform(Receiver::Id) { it.value }
    val text = text("text")
    val statusType = enum<StatusType>("status_type")

    override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean) = Sms(
        id = row.column(id),
        receiver = Receiver(id = row.column(receiverId), phoneNumber = row.column(Receivers.phoneNumber)),
        text = row.column(text),
        status = doCreateStatus(row)
    )

    private fun doCreateStatus(row: QueryRowSet): Sms.Status = when (row.column(statusType)) {
        StatusType.QUEUED -> Sms.Status.Queued
        StatusType.SENDING -> Sms.Status.Sending
        StatusType.DONE -> Sms.Status.Done
        StatusType.FAILED -> Sms.Status.Failed
    }

    internal enum class StatusType {
        QUEUED,
        SENDING,
        DONE,
        FAILED,
    }
}

internal val Database.messages get() = this.sequenceOf(SMSes)
