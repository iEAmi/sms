package me.ahmad.sms.domain

interface ProviderRepository {
    fun all(): List<Provider>

    fun update(provider: Provider): Provider
}