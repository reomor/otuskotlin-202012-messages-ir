package ru.otus.kotlin.messaging.app.spring.controller

import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.ok
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageResponse
import ru.otus.kotlin.messaging.mapper.context.TransportContext
import ru.otus.kotlin.messaging.mapper.context.fromContext
import ru.otus.kotlin.messaging.mapper.context.setRequest

class MessagingController {
    fun create(serverRequest: ServerRequest): ServerResponse {
        val request = serverRequest.body(CreateChannelMessageRequest::class.java)
        val context = TransportContext()
        context.commonContext.request = request
        context.messagingContext.setRequest(request)

        return ok()
            .body(CreateChannelMessageResponse().fromContext(context))
    }
}