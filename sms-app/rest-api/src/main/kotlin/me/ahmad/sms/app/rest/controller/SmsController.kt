package me.ahmad.sms.app.rest.controller

import com.linecorp.armeria.common.MediaTypeNames
import com.linecorp.armeria.server.annotation.Get
import com.linecorp.armeria.server.annotation.Param
import com.linecorp.armeria.server.annotation.Produces
import me.ahmad.sms.domain.service.QueueSmsService
import me.ahmad.sms.domain.toPhoneNumber

internal class SmsController(
    private val queueSmsService: QueueSmsService
) {

    @Get("/send")
    @Produces(MediaTypeNames.JSON_UTF_8)
    fun send(@Param("number") receiver: String, @Param("body") text: String): Boolean {
        val phoneNumber = receiver.toPhoneNumber()
        queueSmsService.queue(phoneNumber, text)

        return true
    }
}