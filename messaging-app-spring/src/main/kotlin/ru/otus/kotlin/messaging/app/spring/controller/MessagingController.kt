package ru.otus.kotlin.messaging.app.spring.controller

import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.ok
import ru.otus.kotlin.messaging.InstantMessage
import ru.otus.kotlin.messaging.ProfileId
import ru.otus.kotlin.messaging.api.model.message.*
import ru.otus.kotlin.messaging.mapper.context.TransportContext
import ru.otus.kotlin.messaging.mapper.context.fromContext
import ru.otus.kotlin.messaging.mapper.context.setRequest
import java.util.*

class MessagingController {

    fun create(serverRequest: ServerRequest): ServerResponse {
        val request = serverRequest.body(CreateChannelMessageRequest::class.java)
        val context = TransportContext().setRequest(request)

        return ok()
            .body(CreateChannelMessageResponse().fromContext(context))
    }

    fun delete(serverRequest: ServerRequest): ServerResponse {
        val request = serverRequest.body(DeleteChannelMessageRequest::class.java)
        val context = TransportContext().setRequest(request)

        return ok()
            .body(DeleteChannelMessageResponse().fromContext(context))
    }

    fun edit(serverRequest: ServerRequest): ServerResponse {
        val request = serverRequest.body(EditChannelMessageRequest::class.java)
        val context = TransportContext().setRequest(request)

        return ok()
            .body(EditChannelMessageResponse().fromContext(context))
    }

    fun get(serverRequest: ServerRequest): ServerResponse {
        val request = serverRequest.body(GetChannelMessageRequest::class.java)
        val context = TransportContext().setRequest(request)

        val profileIdTo = ProfileId(UUID.randomUUID().toString())
        val profileIdFrom = ProfileId(UUID.randomUUID().toString())
        context.messagingContext.messages = listOf(
            InstantMessage(
                profileIdTo = profileIdTo,
                profileIdFrom = profileIdFrom,
                messageText = "message1"
            ),
            InstantMessage(
                profileIdTo = profileIdTo,
                profileIdFrom = profileIdFrom,
                messageText = "message2"
            )
        )

        return ok()
            .body(GetChannelMessageResponse().fromContext(context))
    }
}
