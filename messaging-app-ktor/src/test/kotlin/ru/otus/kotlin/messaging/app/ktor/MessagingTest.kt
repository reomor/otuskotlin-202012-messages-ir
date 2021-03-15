package ru.otus.kotlin.messaging.app.ktor

import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Test
import ru.otus.kotlin.messaging.api.model.common.AbstractRequest
import ru.otus.kotlin.messaging.api.model.common.AbstractResponse
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageDto
import ru.otus.kotlin.messaging.mapper.openapi.CreateChannelRequestSerializer
import ru.otus.kotlin.messaging.mapper.openapi.CreateChannelResponseSerializer
import ru.otus.kotlin.messaging.mapper.openapi.generalRequestResponseSerializer
import ru.otus.kotlin.messaging.openapi.channel.models.BaseMessage
import ru.otus.kotlin.messaging.openapi.channel.models.ChannelDto
import ru.otus.kotlin.messaging.openapi.channel.models.ChannelType
import ru.otus.kotlin.messaging.openapi.channel.models.CreateChannelRequest
import java.time.LocalDateTime
import java.util.*
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
                setBody(generalRequestResponseSerializer.encodeToString(AbstractRequest.serializer(), request))
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val responseBody = generalRequestResponseSerializer.decodeFromString(
                    AbstractResponse.serializer(),
                    response.content ?: ""
                )
                assertEquals(request, responseBody.request)
            }
        }
    }

    @Test
    fun createChannel() {
        withTestApplication({ module(testing = true) }) {

            val request: BaseMessage = CreateChannelRequest(
                type = "CreateChannelRequest",
                requestId = UUID.randomUUID().toString(),
                requestTime = LocalDateTime.now().toString(),
                channel = ChannelDto(
                    id = UUID.randomUUID().toString(),
                    name = "channelName",
                    ownerId = UUID.randomUUID().toString(),
                    type = ChannelType.PUBLIC_CHANNEL
                )
            )

            handleRequest(HttpMethod.Post, ChannelApi.baseUri + ChannelApi.createChannelUri) {
                setBody(generalRequestResponseSerializer.encodeToString(CreateChannelRequestSerializer, request as CreateChannelRequest))
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val responseBody = generalRequestResponseSerializer.decodeFromString(
                    CreateChannelResponseSerializer,
                    response.content ?: ""
                )
                assertEquals(request, responseBody.request)
            }
        }
    }
}
