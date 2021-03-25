package ru.otus.kotlin.messaging.app.ktor

import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.otus.kotlin.messaging.app.ktor.common.CommonTest.contentType
import ru.otus.kotlin.messaging.app.ktor.service.ChannelService
import ru.otus.kotlin.messaging.business.backend.operation.stub.Stubs
import ru.otus.kotlin.messaging.mapper.context.toDto
import ru.otus.kotlin.messaging.mapper.openapi.*
import ru.otus.kotlin.messaging.openapi.channel.models.*
import kotlin.test.assertEquals

internal class ChannelTest {

    @Test
    fun createChannel() {
        withTestApplication({ module(testing = true) }) {

            val request: BaseMessage = CreateChannelRequest(
                type = "CreateChannelRequest",
                requestId = "c11dcf66-57fa-495b-af21-162a6dfcbffa",
                requestTime = "2021-03-21T18:16:55.351733200",
                channel = Stubs.channel.toDto()
            )

            handleRequest(HttpMethod.Post, ChannelApi.baseUri + ChannelApi.createChannelUri) {
                setBody(
                    generalRequestResponseSerializer.encodeToString(
                        CreateChannelRequestSerializer,
                        request as CreateChannelRequest
                    )
                )
                addHeader(HttpHeaders.ContentType, contentType)
            }.apply {

                assertEquals(HttpStatusCode.OK, response.status())

                val responseBody = generalRequestResponseSerializer.decodeFromString(
                    CreateChannelResponseSerializer,
                    response.content ?: ""
                )

                assertEquals(
                    Stubs.createChannelResponse.copy(request = request),
                    responseBody
                )
            }
        }
    }

    @Test
    fun deleteChannel() {
        withTestApplication({ module(testing = true) }) {

            val request: BaseMessage = DeleteChannelRequest(
                type = "DeleteChannelRequest",
                requestId = "c11dcf66-57fa-495b-af21-162a6dfcbffa",
                requestTime = "2021-03-21T18:16:55.351733200",
                channelId = ChannelService.channelDto.id
            )

            handleRequest(HttpMethod.Post, ChannelApi.baseUri + ChannelApi.deleteChannelUri) {
                setBody(
                    generalRequestResponseSerializer.encodeToString(
                        DeleteChannelRequestSerializer,
                        request as DeleteChannelRequest
                    )
                )
                addHeader(HttpHeaders.ContentType, contentType)
            }.apply {

                assertEquals(HttpStatusCode.OK, response.status())

                val responseBody = generalRequestResponseSerializer.decodeFromString(
                    DeleteChannelResponseSerializer,
                    response.content ?: ""
                )

                assertEquals(
                    ChannelService.deleteChannelResponse.copy(request = request),
                    responseBody
                )
            }
        }
    }

    @Test
    fun getChannel() {
        withTestApplication({ module(testing = true) }) {

            val request: BaseMessage = GetChannelRequest(
                type = "GetChannelRequest",
                requestId = "c11dcf66-57fa-495b-af21-162a6dfcbffa",
                requestTime = "2021-03-21T18:16:55.351733200",
                filter = ChannelFilterDto(
                    channelIds = listOf(ChannelService.channelDto.id!!),
                    pageSize = 100,
                    pageNumber = 0
                )
            )

            handleRequest(HttpMethod.Post, ChannelApi.baseUri + ChannelApi.getChannelUri) {
                setBody(
                    generalRequestResponseSerializer.encodeToString(
                        GetChannelRequestSerializer,
                        request as GetChannelRequest
                    )
                )
                addHeader(HttpHeaders.ContentType, contentType)
            }.apply {

                assertEquals(HttpStatusCode.OK, response.status())

                val responseBody = generalRequestResponseSerializer.decodeFromString(
                    GetChannelResponseSerializer,
                    response.content ?: ""
                )

                assertEquals(
                    ChannelService.getChannelResponse.copy(request = request),
                    responseBody
                )
            }
        }
    }
}
