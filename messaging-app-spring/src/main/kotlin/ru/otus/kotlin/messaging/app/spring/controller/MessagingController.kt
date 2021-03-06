package ru.otus.kotlin.messaging.app.spring.controller

import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.ok
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageResponse
import java.time.LocalDateTime
import java.util.*

class MessagingController {
    fun create(serverRequest: ServerRequest): ServerResponse {
        val request = serverRequest.body(CreateChannelMessageRequest::class.java)
        return ok().body(
            CreateChannelMessageResponse(
                responseId = UUID.randomUUID().toString(),
                responseTime = LocalDateTime.now().toString(),
                request = request
            )
        )
    }
}