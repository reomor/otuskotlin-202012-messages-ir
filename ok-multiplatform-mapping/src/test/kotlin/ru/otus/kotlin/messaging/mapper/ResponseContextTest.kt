package ru.otus.kotlin.messaging.mapper

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.otus.kotlin.messaging.InstantMessage
import ru.otus.kotlin.messaging.ProfileId
import ru.otus.kotlin.messaging.api.model.common.dto.CommonResponseStatus
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageResponse
import ru.otus.kotlin.messaging.api.model.message.GetChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.GetChannelMessageResponse
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageDto
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageFilter
import ru.otus.kotlin.messaging.mapper.context.TransportContext
import ru.otus.kotlin.messaging.mapper.context.toDto
import java.time.LocalDateTime
import java.util.*

internal class ResponseContextTest {

    @Test
    fun createPersonalMessage() {
        val context = TransportContext()

        val request = CreateChannelMessageRequest(
            requestId = "1234",
            requestTime = LocalDateTime.now().toString(),
            data = ChannelMessageDto(
                profileIdFrom = "1234abc",
                profileIdTo = "cba1234",
                messageText = "Hello World"
            )
        )

        context.commonContext.request = request

        val baseResponse = CreateChannelMessageResponse(
            responseId = UUID.randomUUID().toString(),
            responseTime = LocalDateTime.now().toString()
        )

        val response = baseResponse.toDto(context)

        assertEquals(
            request,
            context.commonContext.request
        )
        assertEquals(
            request,
            response.request
        )
        assertEquals(
            CommonResponseStatus.SUCCESS,
            response.status
        )
    }

    @Test
    fun getChannelMessage() {
        val context = TransportContext()

        val requestData = ChannelMessageFilter(
            profileIdFrom = "1234abc",
            channelId = "channelId",
            pageSize = 100,
            pageNumber = 0
        )
        val request = GetChannelMessageRequest(
            requestId = "1234",
            requestTime = LocalDateTime.now().toString(),
            filter = requestData
        )
        val messages = listOf(
            InstantMessage(
                profileIdTo = ProfileId("cba4321"),
                profileIdFrom = ProfileId(requestData.profileIdFrom!!),
                messageText = "message1"
            ),
            InstantMessage(
                profileIdTo = ProfileId("cba4321"),
                profileIdFrom = ProfileId(requestData.profileIdFrom!!),
                messageText = "message2"
            )
        )

        context.commonContext.request = request
        context.messagingContext.messages = messages

        val baseResponse = GetChannelMessageResponse(
            responseId = UUID.randomUUID().toString(),
            responseTime = LocalDateTime.now().toString(),
        )

        val response = baseResponse.toDto(context)

        assertEquals(
            request,
            context.commonContext.request
        )
        assertEquals(
            request,
            response.request
        )
        assertEquals(
            messages.size,
            response.data.size
        )
        assertEquals(
            CommonResponseStatus.SUCCESS,
            response.status
        )
    }
}
