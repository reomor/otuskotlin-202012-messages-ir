package ru.otus.kotlin.messaging.app.ktor.controller

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import ru.otus.kotlin.messaging.api.model.common.AbstractResponse
import ru.otus.kotlin.messaging.api.model.common.Request
import ru.otus.kotlin.messaging.api.model.common.dto.CommonResponseStatus
import ru.otus.kotlin.messaging.api.model.common.error.CommonErrorDto
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageResponse
import ru.otus.kotlin.messaging.api.model.message.serialization.requestResponseSerializer
import ru.otus.kotlin.messaging.app.ktor.controller.Common.jsonContentType

@OptIn(InternalSerializationApi::class)
suspend inline fun <reified REQ : Request, reified RES : AbstractResponse> PipelineContext<Unit, ApplicationCall>.handleRequest(
    block: (REQ) -> RES
) {
    try {
        val request = call.receive<Request>() as REQ
        call.respondText(
            requestResponseSerializer.encodeToString(AbstractResponse::class.serializer(), block(request)),
            contentType = jsonContentType
        )
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