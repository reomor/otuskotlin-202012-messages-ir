package ru.otus.kotlin.messaging.mapper

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.otus.kotlin.messaging.Channel
import ru.otus.kotlin.messaging.ChannelId
import ru.otus.kotlin.messaging.InstantMessage
import ru.otus.kotlin.messaging.ProfileId
import ru.otus.kotlin.messaging.api.model.common.dto.CommonResponseStatus
import ru.otus.kotlin.messaging.api.model.message.*
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageDto
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageFilter
import ru.otus.kotlin.messaging.mapper.context.TransportContext
import ru.otus.kotlin.messaging.mapper.context.toDto
import ru.otus.kotlin.messaging.openapi.channel.models.*
import java.time.LocalDateTime
import java.util.*

internal class ResponseContextTest {

    @Test
    fun createChannelMessage() {

        val request = CreateChannelMessageRequest(
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            data = ChannelMessageDto(
                profileIdFrom = "1234abc",
                profileIdTo = "cba1234",
                messageText = "Hello World"
            )
        )

        val context = TransportContext()
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
    fun deleteChannelMessage() {

        val request = DeleteChannelMessageRequest(
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            channelId = UUID.randomUUID().toString(),
            messageId = UUID.randomUUID().toString()
        )

        val context = TransportContext()
        context.commonContext.request = request

        val baseResponse = DeleteChannelMessageResponse(
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
    fun editChannelMessage() {

        val request = EditChannelMessageRequest(
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            channelId = UUID.randomUUID().toString(),
            messageId = UUID.randomUUID().toString()
        )

        val context = TransportContext()
        context.commonContext.request = request

        val baseResponse = EditChannelMessageResponse(
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

        val requestData = ChannelMessageFilter(
            profileIdFrom = "1234abc",
            channelId = "channelId",
            pageSize = 100,
            pageNumber = 0
        )
        val request = GetChannelMessageRequest(
            requestId = UUID.randomUUID().toString(),
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

        val context = TransportContext()
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

    @Test
    fun createChannel() {

        val channelDto = ChannelDto(
            UUID.randomUUID().toString(),
            "channel",
            "1234abc",
            type = ChannelType.PUBLIC_CHANNEL
        )
        val request = CreateChannelRequest(
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            channel = channelDto
        )

        val context = TransportContext()
        context.openApiContext.request = request
        context.messagingContext.channel = Channel(
            ChannelId(channelDto.id!!),
            name = channelDto.name!!,
            ownerId = ProfileId(channelDto.ownerId!!),
            type = ru.otus.kotlin.messaging.ChannelType.valueOf(channelDto.type!!.value.toUpperCase())
        )

        val baseResponse = CreateChannelResponse(
            responseId = UUID.randomUUID().toString(),
            responseTime = LocalDateTime.now().toString(),
        )

        val response = baseResponse.toDto(context)

        assertEquals(
            request,
            context.openApiContext.request
        )
        assertEquals(
            response,
            context.openApiContext.response
        )
        assertEquals(
            request,
            response.request
        )
        assertEquals(
            channelDto,
            response.channel
        )
        assertEquals(
            ResponseStatus.SUCCESS,
            response.status
        )
    }

    @Test
    fun getChannel() {

        val channelName = "channel"
        val filter = ChannelFilterDto(
            listOf(channelName),
            pageSize = 50,
            pageNumber = 0
        )
        val request = GetChannelRequest(
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            filter = filter
        )

        val context = TransportContext()
        context.openApiContext.request = request
        context.messagingContext.channels = listOf(
            Channel(
                channelId = ChannelId(UUID.randomUUID().toString()),
                name = channelName,
                ownerId = ProfileId("1234abc"),
                type = ru.otus.kotlin.messaging.ChannelType.PUBLIC_CHANNEL
            )
        )

        val baseResponse = GetChannelResponse(
            responseId = UUID.randomUUID().toString(),
            responseTime = LocalDateTime.now().toString(),
        )

        val response = baseResponse.toDto(context)

        assertEquals(
            request,
            context.openApiContext.request
        )
        assertEquals(
            response,
            context.openApiContext.response
        )
        assertEquals(
            request,
            response.request
        )
        assertTrue(response.channels?.map { it.name }?.toSet()?.contains(channelName) ?: false)
        assertEquals(
            ResponseStatus.SUCCESS,
            response.status
        )
    }

    @Test
    fun deleteChannel() {

        val request = DeleteChannelRequest(
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            channelId = UUID.randomUUID().toString()
        )

        val context = TransportContext()
        context.openApiContext.request = request
        context.messagingContext.channel = Channel(
            channelId = ChannelId(request.channelId!!),
            name = "channelName",
            ownerId = ProfileId("1234abc"),
            type = ru.otus.kotlin.messaging.ChannelType.PUBLIC_CHANNEL
        )

        val baseResponse = DeleteChannelResponse(
            responseId = UUID.randomUUID().toString(),
            responseTime = LocalDateTime.now().toString(),
        )

        val response = baseResponse.toDto(context)

        assertEquals(
            request,
            context.openApiContext.request
        )
        assertEquals(
            response,
            context.openApiContext.response
        )
        assertEquals(
            request,
            response.request
        )
        assertEquals(
            request.channelId,
            response.channel?.id
        )
        assertEquals(
            ResponseStatus.SUCCESS,
            response.status
        )
    }
}
