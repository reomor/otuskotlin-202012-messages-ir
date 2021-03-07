package ru.otus.kotlin.messaging.app.ktor

import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.kotlin.messaging.api.model.common.AbstractRequest
import ru.otus.kotlin.messaging.api.model.common.AbstractResponse
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageDto
import ru.otus.kotlin.messaging.api.model.message.serialization.requestResponseSerializer
import java.time.LocalDateTime
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals


class MessagingTest {

    @Test
    fun createMessage() {
        withTestApplication({ module(testing = true) }) {

            val request: AbstractRequest = CreateChannelMessageRequest(
                requestId = UUID.randomUUID().toString(),
                requestTime = LocalDateTime.now().toString(),
                data = ChannelMessageDto(
                    profileIdFrom = UUID.randomUUID().toString(),
                    profileIdTo = UUID.randomUUID().toString(),
                    messageText = "Text"
                )
            )

            handleRequest(HttpMethod.Post, MessagingApi.baseUri + MessagingApi.createMessageUri) {
                setBody(requestResponseSerializer.encodeToString(AbstractRequest.serializer(), request))
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val responseBody = requestResponseSerializer.decodeFromString(
                    AbstractResponse.serializer(),
                    response.content ?: ""
                )
                assertEquals(request, responseBody.request)
            }
        }
    }
}