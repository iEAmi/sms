package me.ahmad.sms.launcher

import com.linecorp.armeria.common.HttpResponse
import com.linecorp.armeria.server.Server


fun main() {
    val sb = Server.builder()
    sb.defaultHostname("localhost")
    sb.http(8080)
    sb.service("/") { _, _ ->
        HttpResponse.of("Hello, world!")
    }

    val server = sb.build()
    server.start().join()
}