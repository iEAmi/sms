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
    val providerId = varchar("provider_id").transform(Provider::Id) { it.value }
    val statusType = enum<StatusType>("status_type")
    val statusQueuedRetry = int("status_queued_retry")

    override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean) = Sms(
        id = row.column(id),
        receiver = Receiver(id = row.column(receiverId), phoneNumber = row.column(Receivers.phoneNumber)),
        text = row.column(text),
        provider = Provider(id = row.column(providerId), address = row.column(Providers.address)),
        status = doCreateStatus(row)
    )

    private fun doCreateStatus(row: QueryRowSet): Sms.Status = when (row.column(statusType)) {
        StatusType.QUEUED -> Sms.Status.Queued(row.columnOrDefault(statusQueuedRetry, 0))
        StatusType.CREATED -> Sms.Status.Created
        StatusType.SAVED -> Sms.Status.Saved
        StatusType.CONSUMED -> Sms.Status.Consumed
        StatusType.SENDING -> Sms.Status.Sending
        StatusType.DONE -> Sms.Status.Done
        StatusType.FAILED -> Sms.Status.Failed
    }

    internal enum class StatusType {
        QUEUED,
        CREATED,
        SAVED,
        CONSUMED,
        SENDING,
        DONE,
        FAILED,
    }
}

internal val Database.messages get() = this.sequenceOf(SMSes)
