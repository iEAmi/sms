package me.ahmad.sms.infra.persistence.repository

import me.ahmad.sms.domain.ProviderRepository
import me.ahmad.sms.domain.ReceiverRepository
import me.ahmad.sms.domain.SmsRepository
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

internal val `repository-module` = DI.Module("repository-module") {
    bind<ProviderRepository> { provider { ProviderRepositoryImpl(instance()) } }
    bind<ReceiverRepository> { provider { ReceiverRepositoryImpl(instance()) } }
    bind<SmsRepository> { provider { SmsRepositoryImpl(instance()) } }
}