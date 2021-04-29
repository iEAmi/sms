package me.ahmad.sms.domain.service

import me.ahmad.sms.domain.Provider
import me.ahmad.sms.domain.ProviderRepository
import me.ahmad.sms.domain.Receiver

class ProviderSelector(private val repo: ProviderRepository) {

    fun select(receiver: Receiver, text: String, exclude: Provider? = null): Provider? {
        val all = repo.all().toMutableList()

        if (exclude != null)
            all.remove(exclude)

        return all.shuffled().first()
    }
}