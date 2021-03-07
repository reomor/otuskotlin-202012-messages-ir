package ru.otus.kotlin.messaging.app.ktor.service

import ru.otus.kotlin.messaging.api.model.common.Response
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageResponse
import ru.otus.kotlin.messaging.mapper.context.TransportContext
import ru.otus.kotlin.messaging.mapper.context.setRequest
import ru.otus.kotlin.messaging.mapper.context.toDto
import java.time.LocalDateTime
import java.util.*

class MessagingService {

    suspend fun create(request: CreateChannelMessageRequest): Response = TransportContext().run {
        commonContext.request = request
        messagingContext.setRequest(request)
        CreateChannelMessageResponse(
            responseId = UUID.randomUUID().toString(),
            responseTime = LocalDateTime.now().toString()
        ).toDto(this)
    }
}