package ru.otus.kotlin.messaging.app.kotless

import io.kotless.dsl.lang.KotlessContext
import io.kotless.dsl.lang.http.Post
import org.slf4j.LoggerFactory
import ru.otus.kotlin.messaging.api.model.message.GetChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.GetChannelMessageResponse
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageDto
import ru.otus.kotlin.messaging.mapper.openapi.generalRequestResponseSerializer
import java.time.LocalDateTime
import java.util.*

private val log = LoggerFactory.getLogger("MessageController")

@Post("/message/get")
fun messageList(): String? = KotlessContext
    .HTTP
    .request
    .myBody?.let { requestString ->
        val request = generalRequestResponseSerializer.decodeFromString(GetChannelMessageRequest.serializer(), requestString)
        val response = GetChannelMessageResponse(
            responseId = UUID.randomUUID().toString(),
            responseTime = LocalDateTime.now().toString(),
            data = listOf(
                ChannelMessageDto(
                    profileIdFrom = UUID.randomUUID().toString(),
                    profileIdTo = UUID.randomUUID().toString(),
                    messageText = "Text message1"
                ),
                ChannelMessageDto(
                    profileIdFrom = UUID.randomUUID().toString(),
                    profileIdTo = UUID.randomUUID().toString(),
                    messageText = "Text message2"
                )
            ),
            request = request
        )
        generalRequestResponseSerializer.encodeToString(GetChannelMessageResponse.serializer(), response)
    }
