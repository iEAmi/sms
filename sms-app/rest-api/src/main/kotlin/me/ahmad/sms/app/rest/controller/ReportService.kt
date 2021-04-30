package me.ahmad.sms.app.rest.controller

import me.ahmad.sms.domain.Provider
import me.ahmad.sms.domain.ProviderRepository
import me.ahmad.sms.domain.Receiver
import me.ahmad.sms.domain.ReceiverRepository
import java.math.BigDecimal
import java.math.RoundingMode

internal class ReportService(
    private val providerRepository: ProviderRepository,
    private val receiverRepository: ReceiverRepository
) {

    fun generateReport(): Report {
        val providers = providerRepository.all()
        val totalSms = providers.fold(0uL) { acc, provider -> acc.plus(provider.totalCount) }

        val totalFailed = providers.fold(0uL) { acc, provider -> acc.plus(provider.failedCount) }
        val totalFailedPercent = if (totalSms == 0uL) 0.00 else (totalFailed.toDouble() / totalSms.toDouble()) * 100

        val totalSuccess = providers.fold(0uL) { acc, provider -> acc.plus(provider.doneCount) }
        val totalSuccessPercent = if (totalSms == 0uL) 0.00 else (totalSuccess.toDouble() / totalSms.toDouble()) * 100


        return Report(
            providers,
            receiverRepository.getTopTen(),
            totalSms,
            totalFailed,
            totalSuccess,
            BigDecimal.valueOf(totalFailedPercent).setScale(2, RoundingMode.HALF_UP).toDouble(),
            BigDecimal.valueOf(totalSuccessPercent).setScale(2, RoundingMode.HALF_UP).toDouble()
        )
    }

    data class Report(
        val providers: List<Provider>,
        val topReceivers: List<Receiver>,
        val totalSms: ULong,
        val totalFailed: ULong,
        val totalSuccess: ULong,
        val totalFailedPercent: Double,
        val totalSuccessPercent: Double
    )
}