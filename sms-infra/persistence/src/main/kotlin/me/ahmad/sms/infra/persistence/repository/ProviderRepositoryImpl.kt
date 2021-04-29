package me.ahmad.sms.infra.persistence.repository

import me.ahmad.sms.domain.Provider
import me.ahmad.sms.domain.ProviderRepository
import me.ahmad.sms.infra.persistence.table.providers
import org.ktorm.database.Database
import org.ktorm.entity.toList
import java.util.*

internal class ProviderRepositoryImpl(
    private val database: Database
) : ProviderRepository {
    private val cache = Collections.synchronizedList(mutableListOf<Provider>())

    override fun all(): List<Provider> {
        if (cache.isNotEmpty())
            return cache.toMutableList()

        cache.addAll(database.providers.toList())

        return cache.toMutableList()
    }
}