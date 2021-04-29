package me.ahmad.sms.infra.persistence.table

import me.ahmad.sms.domain.Provider
import org.ktorm.database.Database
import org.ktorm.dsl.QueryRowSet
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.double
import org.ktorm.schema.long
import org.ktorm.schema.varchar

internal object Providers : RichTable<Provider>("providers") {
    val id = varchar("id").primaryKey().transform(Provider::Id) { it.value }
    val address = varchar("address").transform(Provider::Address) { it.value }
    val totalCount = long("total_count")
    val doneCount = long("done_count")
    val donePercent = double("done_percent")
    val failedCount = long("failed_count")
    val failedPercent = double("failed_percent")

    override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean) = Provider(
        id = row.column(id),
        address = row.column(address),
        totalCount = row.column(totalCount).toULong(),
        doneCount = row.column(doneCount).toULong(),
        donePercent = row.column(donePercent),
        failedCount = row.column(failedCount).toULong(),
        failedPercent = row.column(failedPercent),
    )
}

internal val Database.providers get() = this.sequenceOf(Providers)