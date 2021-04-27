package me.ahmad.sms.app.rest

import com.linecorp.armeria.server.annotation.Get
import com.linecorp.armeria.server.annotation.ServiceName

@ServiceName("sms")
class SmsController {

    @Get("send")
    fun send() {

    }
}