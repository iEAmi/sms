package me.ahmad.sms.app.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.linecorp.armeria.common.*
import com.linecorp.armeria.server.ServiceRequestContext
import com.linecorp.armeria.server.annotation.ExceptionHandlerFunction
import me.ahmad.sms.defs.ProblemDetails
import me.ahmad.sms.domain.SmsException

internal class SmsExceptionHandler(
    private val mapper: ObjectMapper
) : ExceptionHandlerFunction {

    override fun handleException(ctx: ServiceRequestContext, req: HttpRequest, cause: Throwable): HttpResponse {
        cause.printStackTrace()
        
        if (cause !is SmsException) return unknownException(cause)

        val statusCode = when (cause) {
            is SmsException.InvalidArgument -> HttpStatus.BAD_REQUEST.code()
            else -> HttpStatus.INTERNAL_SERVER_ERROR.code()
        }

        val problem = ProblemDetails(statusCode, cause.title, cause.type, cause.details)

        return problemToResponse(problem)
    }

    private fun unknownException(cause: Throwable): HttpResponse {
        val ex = SmsException.wrap(cause)
        val problem = ProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR.code(), ex.title, ex.type, ex.details)

        return problemToResponse(problem)
    }

    private fun problemToResponse(problem: ProblemDetails): HttpResponse =
        HttpResponse.of(
            HttpStatus.valueOf(problem.status),
            MediaType.JSON_UTF_8,
            HttpData.copyOf(mapper.writeValueAsBytes(problem))
        )
}