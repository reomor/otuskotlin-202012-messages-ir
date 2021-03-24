package ru.otus.kotlin.messaging.app.ktor

import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.otus.kotlin.messaging.api.model.common.AbstractRequest
import ru.otus.kotlin.messaging.api.model.common.AbstractResponse
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.DeleteChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.EditChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.GetChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageDto
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageFilter
import ru.otus.kotlin.messaging.app.ktor.common.CommonTest.contentType
import ru.otus.kotlin.messaging.app.ktor.service.MessagingService
import ru.otus.kotlin.messaging.mapper.openapi.generalRequestResponseSerializer
import kotlin.test.assertEquals

internal class MessagingTest {

    @Test
    fun createMessage() {
        withTestApplication({ module(testing = true) }) {

            val request: AbstractRequest = CreateChannelMessageRequest(
                requestId = "c11dcf66-57fa-495b-af21-162a6dfcbffa",
                requestTime = "2021-03-21T18:16:55.351733200",
                data = ChannelMessageDto(
                    profileIdFrom = "from4458-27ee-4edc-b354-1787c80cc7cf",
                    profileIdTo = "to136bea-9a5d-4737-9deb-c039262427d5",
                    messageText = "Text"
                )
            )

            handleRequest(HttpMethod.Post, MessagingApi.baseUri + MessagingApi.createMessageUri) {
                setBody(generalRequestResponseSerializer.encodeToString(AbstractRequest.serializer(), request))
                addHeader(HttpHeaders.ContentType, contentType)
            }.apply {

                assertEquals(HttpStatusCode.OK, response.status())

                val responseBody = generalRequestResponseSerializer.decodeFromString(
                    AbstractResponse.serializer(),
                    response.content ?: ""
                )

                assertEquals(
                    MessagingService.createChannelMessageResponse.copy(request = request),
                    responseBody
                )
            }
        }
    }

    @Test
    fun deleteMessage() {
        withTestApplication({ module(testing = true) }) {

            val request: AbstractRequest = DeleteChannelMessageRequest(
                requestId = "c11dcf66-57fa-495b-af21-162a6dfcbffa",
                requestTime = "2021-03-21T18:16:55.351733200",
                messageId = "4c7730f0-48cf-453f-a129-9445c31effb6",
                channelId = "a70e38cf-c052-46d4-92ed-5c9a25eb9f6b"
            )

            handleRequest(HttpMethod.Post, MessagingApi.baseUri + MessagingApi.deleteMessageUri) {
                setBody(generalRequestResponseSerializer.encodeToString(AbstractRequest.serializer(), request))
                addHeader(HttpHeaders.ContentType, contentType)
            }.apply {

                assertEquals(HttpStatusCode.OK, response.status())

                val responseBody = generalRequestResponseSerializer.decodeFromString(
                    AbstractResponse.serializer(),
                    response.content ?: ""
                )

                assertEquals(
                    MessagingService.deleteChannelMessageResponse.copy(request = request),
                    responseBody
                )
            }
        }
    }

    @Test
    fun editMessage() {
        withTestApplication({ module(testing = true) }) {

            val request: AbstractRequest = EditChannelMessageRequest(
                requestId = "c11dcf66-57fa-495b-af21-162a6dfcbffa",
                requestTime = "2021-03-21T18:16:55.351733200",
                messageId = "4c7730f0-48cf-453f-a129-9445c31effb6",
                channelId = "a70e38cf-c052-46d4-92ed-5c9a25eb9f6b",
                data = MessagingService.channelMessageDto
            )

            handleRequest(HttpMethod.Post, MessagingApi.baseUri + MessagingApi.editMessageUri) {
                setBody(generalRequestResponseSerializer.encodeToString(AbstractRequest.serializer(), request))
                addHeader(HttpHeaders.ContentType, contentType)
            }.apply {

                assertEquals(HttpStatusCode.OK, response.status())

                val responseBody = generalRequestResponseSerializer.decodeFromString(
                    AbstractResponse.serializer(),
                    response.content ?: ""
                )

                assertEquals(
                    MessagingService.editChannelMessageResponse.copy(request = request),
                    responseBody
                )
            }
        }
    }

    @Test
    fun getMessage() {
        withTestApplication({ module(testing = true) }) {

            val request: AbstractRequest = GetChannelMessageRequest(
                requestId = "c11dcf66-57fa-495b-af21-162a6dfcbffa",
                requestTime = "2021-03-21T18:16:55.351733200",
                filter = ChannelMessageFilter(
                    profileIdFrom = "f6d60868-981e-4cc7-b6a7-95bcf6e70476",
                    channelId = "712dea1c-64a9-4970-b63e-5f060a11f617",
                    pageSize = 100,
                    pageNumber = 1
                )
            )

            handleRequest(HttpMethod.Post, MessagingApi.baseUri + MessagingApi.getMessageUri) {
                setBody(generalRequestResponseSerializer.encodeToString(AbstractRequest.serializer(), request))
                addHeader(HttpHeaders.ContentType, contentType)
            }.apply {

                assertEquals(HttpStatusCode.OK, response.status())

                val responseBody = generalRequestResponseSerializer.decodeFromString(
                    AbstractResponse.serializer(),
                    response.content ?: ""
                )

                assertEquals(
                    MessagingService.getChannelMessageResponse.copy(request = request),
                    responseBody
                )
            }
        }
    }
}
