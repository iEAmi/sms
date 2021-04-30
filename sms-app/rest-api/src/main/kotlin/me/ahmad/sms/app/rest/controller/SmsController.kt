package me.ahmad.sms.app.rest.controller

import com.linecorp.armeria.common.MediaTypeNames
import com.linecorp.armeria.server.annotation.*
import me.ahmad.sms.domain.service.QueueSmsService
import me.ahmad.sms.domain.toPhoneNumber

@ServiceName("sms")
internal class SmsController(
    private val queueSmsService: QueueSmsService
) {

    @Get("/send")
    @Produces(MediaTypeNames.JSON_UTF_8)
    @Consumes(MediaTypeNames.JSON_UTF_8)
    fun send(@Param("number") receiver: String, @Param("body") text: String): Boolean {
        val phoneNumber = receiver.toPhoneNumber()
        queueSmsService.queue(phoneNumber, text)

        return true
    }
}