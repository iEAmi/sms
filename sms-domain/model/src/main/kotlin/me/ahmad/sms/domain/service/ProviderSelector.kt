package me.ahmad.sms.domain.service

import me.ahmad.sms.domain.Provider
import me.ahmad.sms.domain.ProviderRepository
import me.ahmad.sms.domain.Receiver

class ProviderSelector(private val repo: ProviderRepository) {

    fun select(receiver: Receiver, text: String): Provider? {
        return null
    }
}