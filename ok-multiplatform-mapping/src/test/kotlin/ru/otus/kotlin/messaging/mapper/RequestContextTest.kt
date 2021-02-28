package ru.otus.kotlin.messaging.mapper

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import ru.otus.kotlin.messaging.MessageId
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.DeleteChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.EditChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.GetChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageDto
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageFilter
import ru.otus.kotlin.messaging.context.MessagingContext
import ru.otus.kotlin.messaging.mapper.context.setRequest
import ru.otus.kotlin.messaging.openapi.channel.models.*
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
            channelId = "channelId",
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
            requestData.channelId,
            context.channelMessage.channelId.id
        )

        assertEquals(
            requestData.messageText,
            context.channelMessage.messageText
        )
    }

    @Test
    fun deleteChannelMessage() {

        val request = DeleteChannelMessageRequest(
            requestId = "1234",
            requestTime = LocalDateTime.now().toString(),
            channelId = UUID.randomUUID().toString(),
            messageId = UUID.randomUUID().toString()
        )

        val context = MessagingContext()
        context.setRequest(request)

        assertEquals(
            request.channelId,
            context.channelId.id
        )
        assertTrue(context.messageIds.contains(MessageId(request.messageId!!)))
    }

    @Test
    fun editChannelMessage() {

        val requestData = ChannelMessageDto(
            profileIdFrom = "1234abc",
            channelId = "channelId",
            messageText = "Hello World"
        )
        val request = EditChannelMessageRequest(
            requestId = "1234",
            requestTime = LocalDateTime.now().toString(),
            channelId = UUID.randomUUID().toString(),
            messageId = UUID.randomUUID().toString(),
            data = requestData
        )

        val context = MessagingContext()
        context.setRequest(request)

        assertEquals(
            requestData.profileIdFrom,
            context.channelMessage.profileIdFrom.id
        )
        assertEquals(
            requestData.channelId,
            context.channelMessage.channelId.id
        )

        assertEquals(
            requestData.messageText,
            context.channelMessage.messageText
        )
        assertEquals(
            request.channelId,
            context.channelId.id
        )
        assertTrue(context.messageIds.contains(MessageId(request.messageId!!)))
    }

    @Test
    fun getChannelMessage() {

        val filter = ChannelMessageFilter(
            profileIdFrom =  UUID.randomUUID().toString(),
            channelId = UUID.randomUUID().toString(),
            messageIds = listOf(UUID.randomUUID().toString()),
            pageSize = 100,
            pageNumber = 1
        )
        val request = GetChannelMessageRequest(
            requestId = "1234",
            requestTime = LocalDateTime.now().toString(),
            filter = filter
        )

        val context = MessagingContext()
        context.setRequest(request)

        assertEquals(
            filter.profileIdFrom,
            context.profileIdFrom.id
        )
        assertEquals(
            filter.channelId,
            context.channelId.id
        )
        assertEquals(
            filter.messageIds,
            context.messageIds.map { it.id }
        )
        assertEquals(
            filter.pageNumber,
            context.page.pageNumber
        )
        assertEquals(
            filter.pageSize,
            context.page.pageSize
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

    @Test
    fun deleteChannelTest() {

        val request = DeleteChannelRequest(
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            channelId = UUID.randomUUID().toString()
        )

        val context = MessagingContext()
        context.setRequest(request)

        assertEquals(
            request.channelId,
            context.channelId.id
        )
    }

    @Test
    fun getChannelTest() {

        val filter = ChannelFilterDto(
            channelIds = listOf(UUID.randomUUID().toString()),
            pageSize = 500,
            pageNumber = 1000
        )
        val request = GetChannelRequest(
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            filter = filter
        )

        val context = MessagingContext()
        context.setRequest(request)

        assertEquals(
            filter.channelIds,
            context.channelIds.map { it.id }
        )
        assertEquals(
            filter.pageNumber,
            context.page.pageNumber
        )
        assertEquals(
            filter.pageSize,
            context.page.pageSize
        )
    }
}
