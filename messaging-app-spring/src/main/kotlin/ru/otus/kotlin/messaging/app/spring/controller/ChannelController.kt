package ru.otus.kotlin.messaging.app.spring.controller

import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import ru.otus.kotlin.messaging.mapper.context.TransportContext
import ru.otus.kotlin.messaging.mapper.context.fromContext
import ru.otus.kotlin.messaging.mapper.context.setRequest
import ru.otus.kotlin.messaging.openapi.channel.models.*

class ChannelController {

    fun create(serverRequest: ServerRequest): ServerResponse {
        val request = serverRequest.body(CreateChannelRequest::class.java)
        val context = TransportContext().setRequest(request)

        return ServerResponse.ok()
            .body(CreateChannelResponse().fromContext(context))
    }

    fun delete(serverRequest: ServerRequest): ServerResponse {
        val request = serverRequest.body(DeleteChannelRequest::class.java)
        val context = TransportContext().setRequest(request)

        return ServerResponse.ok()
            .body(DeleteChannelResponse().fromContext(context))
    }

    fun get(serverRequest: ServerRequest): ServerResponse {
        val request = serverRequest.body(GetChannelRequest::class.java)
        val context = TransportContext().setRequest(request)

        return ServerResponse.ok()
            .body(GetChannelResponse().fromContext(context))
    }
}