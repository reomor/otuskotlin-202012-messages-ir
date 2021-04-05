package ru.otus.kotlin.messaging.app.ktor.controller

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.otus.kotlin.messaging.api.model.common.Request
import ru.otus.kotlin.messaging.api.model.common.dto.CommonResponseStatus
import ru.otus.kotlin.messaging.api.model.common.error.CommonErrorDto
import ru.otus.kotlin.messaging.api.model.message.*
import ru.otus.kotlin.messaging.app.ktor.MessagingApi
import ru.otus.kotlin.messaging.app.ktor.service.MessagingService

/**
 * Message API
 */
fun Route.messageRoute(messagingService: MessagingService) {

    route(MessagingApi.baseUri) {

        post(MessagingApi.createMessageUri) {
            handleRequest<CreateChannelMessageRequest, CreateChannelMessageResponse> { request ->
                messagingService.create(request)
            }
        }

        post(MessagingApi.deleteMessageUri) {
            try {
                val request = call.receive<Request>() as DeleteChannelMessageRequest
                val response = messagingService.delete(request)
                call.respond(response)
            } catch (e: Exception) {
                call.respond(
                    DeleteChannelMessageResponse(
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

        post(MessagingApi.editMessageUri) {
            try {
                val request = call.receive<Request>() as EditChannelMessageRequest
                val response = messagingService.edit(request)
                call.respond(response)
            } catch (e: Exception) {
                call.respond(
                    EditChannelMessageResponse(
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

        post(MessagingApi.getMessageUri) {
            try {
                val request = call.receive<Request>() as GetChannelMessageRequest
                val response = messagingService.get(request)
                call.respond(response)
            } catch (e: Exception) {
                call.respond(
                    GetChannelMessageResponse(
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
    }
}
