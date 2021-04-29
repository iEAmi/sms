package me.ahmad.sms.infra.persistence.repository

import me.ahmad.sms.domain.Sms
import me.ahmad.sms.domain.SmsRepository
import me.ahmad.sms.infra.persistence.table.SMSes
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.insertAndGenerateKey
import org.ktorm.dsl.update

internal class SmsRepositoryImpl(private val database: Database) : SmsRepository {
    override fun save(sms: Sms): Sms.Id {
        val id = database.insertAndGenerateKey(SMSes) {
            set(it.receiverId, sms.receiver.id)
            set(it.text, sms.text)
            set(it.status, resolveStatusType(sms.status))
        }

        return Sms.Id(id as Long)
    }

    override fun update(sms: Sms) {
        database.update(SMSes) {
            where { it.id eq sms.id }
            set(it.receiverId, sms.receiver.id)
            set(it.text, sms.text)
            set(it.status, resolveStatusType(sms.status))
        }
    }

    private fun resolveStatusType(status: Sms.Status) = when (status) {
        Sms.Status.Done -> SMSes.StatusType.DONE
        Sms.Status.Failed -> SMSes.StatusType.FAILED
        Sms.Status.Queued -> SMSes.StatusType.QUEUED
        Sms.Status.Sending -> SMSes.StatusType.SENDING
    }
}