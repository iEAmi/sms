package me.ahmad.sms.infra.persistence.repository

import me.ahmad.sms.domain.PhoneNumber
import me.ahmad.sms.domain.Receiver
import me.ahmad.sms.domain.ReceiverRepository
import me.ahmad.sms.infra.persistence.table.Receivers
import me.ahmad.sms.infra.persistence.table.SMSes
import me.ahmad.sms.infra.persistence.table.receivers
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.find

internal class ReceiverRepositoryImpl(private val database: Database) : ReceiverRepository {
    override fun get(byPhoneNumber: PhoneNumber): Receiver? {
        return database.receivers.find { it.phoneNumber eq byPhoneNumber }
    }

    override fun getTopTen(): List<Receiver> = database.from(Receivers)
        .leftJoin(SMSes, on = SMSes.receiverId eq Receivers.id)
        .select(Receivers.id, Receivers.phoneNumber, count())
        .groupBy(Receivers.id, Receivers.phoneNumber)
        .orderBy(count().desc())
        .limit(10)
        .map(Receivers::from)
        .fold(mutableListOf<Receiver>()) { acc, receiver -> acc.add(receiver); acc }

    override fun save(receiver: Receiver): Receiver {
        val id = database.insertAndGenerateKey(Receivers) {
            set(it.phoneNumber, receiver.phoneNumber)
        }

        return receiver.copy(id = id as Receiver.Id)
    }
}