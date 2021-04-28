package me.ahmad.sms.infra.persistence.table

import me.ahmad.sms.infra.persistence.PersistenceException
import org.ktorm.dsl.QueryRowSet
import org.ktorm.schema.BaseTable
import org.ktorm.schema.Column

internal abstract class RichTable<T : Any>(tableName: String) : BaseTable<T>(tableName) {

    protected fun <T : Any> QueryRowSet.column(c: Column<T>): T =
        this[c] ?: nullColumn("$tableName.${c.name} is null.")

    protected fun <T : Any> QueryRowSet.columnOrDefault(c: Column<T>, default: T): T =
        this[c] ?: default

    protected fun <T : Any> QueryRowSet.nullableColumn(c: Column<T>): T? = this[c]

    private fun nullColumn(title: String): Nothing = throw PersistenceException(title)
}