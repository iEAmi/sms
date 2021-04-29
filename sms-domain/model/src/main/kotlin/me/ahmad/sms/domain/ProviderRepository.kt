package me.ahmad.sms.domain

interface ProviderRepository {
    fun all(): List<Provider>
}