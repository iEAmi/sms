package me.ahmad.sms.app.rest.controller

import com.linecorp.armeria.common.MediaTypeNames
import com.linecorp.armeria.server.annotation.*
import me.ahmad.sms.app.SmsFacadeService
import me.ahmad.sms.domain.toPhoneNumber

@ServiceName("sms")
internal class SmsController(
    private val facadeService: SmsFacadeService
) {

    @Get("/send")
    @Produces(MediaTypeNames.JSON_UTF_8)
    @Consumes(MediaTypeNames.JSON_UTF_8)
    fun send(@Param("number") receiver: String, @Param("body") text: String): Boolean {
        // TODO : publish new sms task received

        val phoneNumber = receiver.toPhoneNumber()
        facadeService.queue(phoneNumber, text)

        return true
    }
}