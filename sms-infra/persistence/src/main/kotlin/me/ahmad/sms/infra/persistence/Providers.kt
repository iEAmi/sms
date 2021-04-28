package me.ahmad.sms.infra.persistence

import me.ahmad.sms.domain.Provider
import org.ktorm.database.Database
import org.ktorm.dsl.QueryRowSet
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.varchar

internal object Providers : RichTable<Provider>("providers") {
    val id = varchar("id").primaryKey().transform(Provider::Id) { it.value }
    val address = varchar("address").transform(Provider::Address) { it.value }

    override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean) = Provider(
        id = row.column(id),
        address = row.column(address)
    )
}

internal val Database.providers get() = this.sequenceOf(Providers)