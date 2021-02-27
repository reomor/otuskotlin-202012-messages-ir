package ru.otus.kotlin.messaging.mapper

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageDto
import ru.otus.kotlin.messaging.context.MessagingContext
import ru.otus.kotlin.messaging.mapper.context.setRequest
import ru.otus.kotlin.messaging.openapi.channel.models.ChannelDto
import ru.otus.kotlin.messaging.openapi.channel.models.ChannelType
import ru.otus.kotlin.messaging.openapi.channel.models.CreateChannelRequest
import java.time.LocalDateTime
import java.util.*

internal class RequestContextTest {

    @Test
    fun createPersonalMessage() {

        val requestData = ChannelMessageDto(
            profileIdFrom = "1234abc",
            profileIdTo = "cba1234",
            messageText = "Hello World"
        )
        val request = CreateChannelMessageRequest(
            requestId = "1234",
            requestTime = LocalDateTime.now().toString(),
            data = requestData
        )

        val context = MessagingContext()
        context.setRequest(request)

        assertEquals(
            requestData.profileIdFrom,
            context.instantMessage.profileIdFrom.id
        )
        assertEquals(
            requestData.profileIdTo,
            context.instantMessage.profileIdTo.id
        )

        assertEquals(
            requestData.messageText,
            context.instantMessage.messageText
        )
    }

    @Test
    fun createChannelMessage() {

        val requestData = ChannelMessageDto(
            profileIdFrom = "1234abc",
            channelIdTo = "channelId",
            messageText = "Hello World"
        )
        val request = CreateChannelMessageRequest(
            requestId = "1234",
            requestTime = LocalDateTime.now().toString(),
            data = requestData
        )

        val context = MessagingContext()
        context.setRequest(request)

        assertEquals(
            requestData.profileIdFrom,
            context.channelMessage.profileIdFrom.id
        )
        assertEquals(
            requestData.channelIdTo,
            context.channelMessage.channelId.id
        )

        assertEquals(
            requestData.messageText,
            context.channelMessage.messageText
        )
    }

    @Test
    fun createChannelTest() {

        val channel = ChannelDto(
            id = UUID.randomUUID().toString(),
            name = "channelName",
            ownerId = "1234abc",
            type = ChannelType.PUBLIC_CHANNEL
        )
        val request = CreateChannelRequest(
            requestId = "1234",
            requestTime = LocalDateTime.now().toString(),
            channel = channel
        )

        val context = MessagingContext()
        context.setRequest(request)

        assertEquals(
            channel.id,
            context.channel.channelId.id
        )
        assertEquals(
            channel.name,
            context.channel.name
        )
        assertEquals(
            channel.ownerId,
            context.channel.ownerId.id
        )
        assertEquals(
            channel.type?.value?.toUpperCase(),
            context.channel.type.name
        )
    }
}
