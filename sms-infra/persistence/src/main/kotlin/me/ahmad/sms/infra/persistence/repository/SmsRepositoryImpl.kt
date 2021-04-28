package me.ahmad.sms.infra.persistence.repository

import me.ahmad.sms.domain.Sms
import me.ahmad.sms.domain.SmsRepository
import me.ahmad.sms.infra.persistence.table.SMSes
import org.ktorm.database.Database
import org.ktorm.dsl.insertAndGenerateKey

internal class SmsRepositoryImpl(private val database: Database) : SmsRepository {
    override fun save(sms: Sms): Sms.Id {
        val id = database.insertAndGenerateKey(SMSes) {
            set(it.receiverId, sms.receiver.id)
            set(it.text, sms.text)
            set(it.providerId, sms.provider.id)
            set(it.statusType, resolveStatusType(sms.status))
            set(it.statusQueuedRetry, resolveStatusQueuedRetry(sms.status))
        }

        return Sms.Id(id as Long)
    }

    private fun resolveStatusType(status: Sms.Status) = when (status) {
        Sms.Status.Done -> SMSes.StatusType.DONE
        Sms.Status.Failed -> SMSes.StatusType.FAILED
        is Sms.Status.Queued -> SMSes.StatusType.QUEUED
        Sms.Status.Sending -> SMSes.StatusType.SENDING
    }

    private fun resolveStatusQueuedRetry(status: Sms.Status): Int? =
        if (status is Sms.Status.Queued) status.retry else null
}