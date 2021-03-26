package ru.otus.kotlin.messaging.app.ktor.controller

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.otus.kotlin.messaging.app.ktor.ChannelApi
import ru.otus.kotlin.messaging.app.ktor.service.ChannelService
import ru.otus.kotlin.messaging.openapi.channel.models.*

/**
 * Channel API
 */
fun Route.channelRoute(channelService: ChannelService) {

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

        post(ChannelApi.deleteChannelUri) {
            try {
                val request = call.receive<DeleteChannelRequest>()
                val response = channelService.delete(request)
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

        post(ChannelApi.getChannelUri) {
            try {
                val request = call.receive<GetChannelRequest>()
                val response = channelService.get(request)
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