package me.ahmad.sms.infra.persistence.repository

import me.ahmad.sms.domain.PhoneNumber
import me.ahmad.sms.domain.Receiver
import me.ahmad.sms.domain.ReceiverRepository
import me.ahmad.sms.infra.persistence.table.Receivers
import me.ahmad.sms.infra.persistence.table.receivers
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.insertAndGenerateKey
import org.ktorm.entity.find

internal class ReceiverRepositoryImpl(private val database: Database) : ReceiverRepository {
    override fun get(byPhoneNumber: PhoneNumber): Receiver? {
        return database.receivers.find { it.phoneNumber eq byPhoneNumber }
    }

    override fun save(receiver: Receiver): Receiver.Id {
        val id = database.insertAndGenerateKey(Receivers) {
            set(it.phoneNumber, receiver.phoneNumber)
        }

        return Receiver.Id(id as Long)
    }
}