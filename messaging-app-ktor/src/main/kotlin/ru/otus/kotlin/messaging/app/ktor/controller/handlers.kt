package ru.otus.kotlin.messaging.app.ktor.controller

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import ru.otus.kotlin.messaging.api.model.common.Request
import ru.otus.kotlin.messaging.api.model.common.Response
import ru.otus.kotlin.messaging.api.model.common.dto.CommonResponseStatus
import ru.otus.kotlin.messaging.api.model.common.error.CommonErrorDto
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageResponse

suspend inline fun <reified REQ : Request, reified RES : Response> PipelineContext<Unit, ApplicationCall>.handleRequest(
    block: (REQ) -> RES
): Unit {
    try {
        val request = call.receive<Request>() as REQ
        val message = block(request)
        call.respond(message)
    } catch (e: Exception) {
        call.respond(
            CreateChannelMessageResponse(
                status = CommonResponseStatus.BAD_REQUEST,
                errors = listOf(
                    CommonErrorDto(
                        message = e.localizedMessage
                    )
                )
            )
        )
    }
}