package me.ahmad.sms.infra.persistence.repository

import me.ahmad.sms.domain.Provider
import me.ahmad.sms.domain.ProviderRepository
import me.ahmad.sms.infra.persistence.table.Providers
import me.ahmad.sms.infra.persistence.table.providers
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.update
import org.ktorm.entity.toList
import java.util.concurrent.ConcurrentHashMap

internal class ProviderRepositoryImpl(
    private val database: Database
) : ProviderRepository {
    private val cache = ConcurrentHashMap<Provider.Id, Provider>()

    override fun all(): List<Provider> {
        if (cache.isNotEmpty())
            return cache.elements().toList()

        database.providers.toList().forEach { cache[it.id] = it }


        return cache.elements().toList()
    }

    override fun update(provider: Provider) {
        database.update(Providers) {
            where { it.id eq provider.id }
            set(it.totalCount, provider.totalCount.toLong())
            set(it.doneCount, provider.doneCount.toLong())
            set(it.donePercent, provider.donePercent)
            set(it.failedCount, provider.failedCount.toLong())
            set(it.failedPercent, provider.failedPercent)
        }

        // update inner cache
        cache[provider.id] = provider
    }
}