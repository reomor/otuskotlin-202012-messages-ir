package ru.otus.kotlin.messaging.app.ktor

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import ru.otus.kotlin.messaging.api.model.common.Request
import ru.otus.kotlin.messaging.api.model.common.dto.CommonResponseStatus
import ru.otus.kotlin.messaging.api.model.common.error.CommonErrorDto
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageResponse
import ru.otus.kotlin.messaging.api.model.message.serialization.requestResponseSerializer
import ru.otus.kotlin.messaging.app.ktor.service.ChannelService
import ru.otus.kotlin.messaging.app.ktor.service.MessagingService
import ru.otus.kotlin.messaging.mapper.openapi.generalRequestResponseSerializer
import ru.otus.kotlin.messaging.openapi.channel.models.*

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    val messagingService = MessagingService()
    val channelService = ChannelService()

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(ContentNegotiation) {
        json(
            contentType = ContentType.Application.Json,
            json = generalRequestResponseSerializer
        )
    }

    routing {

        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }

        route(MessagingApi.baseUri) {
            post(MessagingApi.createMessageUri) {
                try {
                    val request = call.receive<Request>() as CreateChannelMessageRequest
                    val response = messagingService.create(request)
                    call.respond(response)
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
        }

        route(ChannelApi.baseUri) {
            post(ChannelApi.createChannelUri) {
                try {
                    val request = call.receive<CreateChannelRequest>()
                    val response = channelService.create(request)
                    call.respond(response)
                } catch (e: Exception) {
                    call.respond(
                        CreateChannelResponse(
                            status = ResponseStatus.BAD_REQUEST,
                            errors = listOf(
                                ErrorDto(
                                    message = e.localizedMessage
                                )
                            )
                        )
                    )
                }
            }
        }
    }
}

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)
