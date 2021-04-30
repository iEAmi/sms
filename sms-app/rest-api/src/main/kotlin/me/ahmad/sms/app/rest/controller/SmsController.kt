package me.ahmad.sms.app.rest.controller

import com.linecorp.armeria.common.HttpResponse
import com.linecorp.armeria.common.MediaType
import com.linecorp.armeria.common.MediaTypeNames
import com.linecorp.armeria.server.annotation.Get
import com.linecorp.armeria.server.annotation.Param
import com.linecorp.armeria.server.annotation.Produces
import me.ahmad.sms.domain.SmsException
import me.ahmad.sms.domain.service.QueueSmsService
import me.ahmad.sms.domain.toPhoneNumber
import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate
import java.util.*

internal class SmsController(
    private val queueSmsService: QueueSmsService,
    private val reportService: ReportService
) {

    @Get("/send")
    @Produces(MediaTypeNames.JSON_UTF_8)
    fun send(@Param("number") receiver: Optional<String>, @Param("body") text: Optional<String>): Boolean {
        if (receiver.isEmpty) throw SmsException.nullArg("number could not be null")
        if (text.isEmpty) throw SmsException.nullArg("body could not be null")

        val phoneNumber = receiver.get().toPhoneNumber()
        queueSmsService.queue(phoneNumber, text.get())

        return true
    }

    @Get("/report")
    @Produces(MediaTypeNames.HTML_UTF_8)
    fun report(): HttpResponse {
        val report = reportService.generateReport()

        val template = JtwigTemplate.classpathTemplate("view/report.twig")
        val model = JtwigModel.newModel().with("report", report)

        val html = template.render(model)

        return HttpResponse.of(MediaType.HTML_UTF_8, html)
    }
}