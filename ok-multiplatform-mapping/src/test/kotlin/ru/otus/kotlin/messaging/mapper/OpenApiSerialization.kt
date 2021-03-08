package ru.otus.kotlin.messaging.mapper

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import ru.otus.kotlin.messaging.mapper.openapi.generalRequestResponseSerializer
import ru.otus.kotlin.messaging.openapi.channel.models.BaseMessage
import ru.otus.kotlin.messaging.openapi.channel.models.ChannelDto
import ru.otus.kotlin.messaging.openapi.channel.models.ChannelType
import ru.otus.kotlin.messaging.openapi.channel.models.CreateChannelRequest
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals

internal class OpenApiSerialization {

    @Test
    fun testUUIDSerialization() {
        val expected = UUID.randomUUID()
        val encodeToString = generalRequestResponseSerializer.encodeToString(expected)
        val actual = generalRequestResponseSerializer.decodeFromString<UUID>(encodeToString)
        assertEquals(expected, actual)
    }

    @Test
    fun testChannelTypeSerialization() {
        val expected = ChannelType.PUBLIC_CHANNEL
        val encodeToString = generalRequestResponseSerializer.encodeToString(expected)
        val actual = generalRequestResponseSerializer.decodeFromString<ChannelType>(encodeToString)
        assertEquals(expected, actual)
    }

    @Test
    fun testChannelDtoSerialization() {
        val expected = ChannelDto(
            id = UUID.randomUUID().toString(),
            name = "channelName",
            ownerId = UUID.randomUUID().toString(),
            type = ChannelType.PUBLIC_CHANNEL
        )

        val encodeToString = generalRequestResponseSerializer.encodeToString(expected)
        val actual = generalRequestResponseSerializer.decodeFromString<ChannelDto>(encodeToString)

        assertEquals(expected, actual)
    }

    @Test
    fun testCreateChannelRequestSerialization() {
        val channel = ChannelDto(
            id = UUID.randomUUID().toString(),
            name = "channelName",
            ownerId = UUID.randomUUID().toString(),
            type = ChannelType.PUBLIC_CHANNEL
        )
        val expected = CreateChannelRequest(
            type = "CreateChannelRequest",
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            channel = channel
        )

        val encodeToString = generalRequestResponseSerializer.encodeToString(expected)
        val actual = generalRequestResponseSerializer.decodeFromString<CreateChannelRequest>(encodeToString)

        assertEquals(expected, actual)
    }

//    @Disabled
    @Test
    fun testCreateChannelRequestPolymorphSerialization() {
        val channel = ChannelDto(
            id = UUID.randomUUID().toString(),
            name = "channelName",
            ownerId = UUID.randomUUID().toString(),
            type = ChannelType.PUBLIC_CHANNEL
        )
        val expected: BaseMessage = CreateChannelRequest(
            type = "CreateChannelRequest",
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            channel = channel
        )

        val encodeToString = generalRequestResponseSerializer.encodeToString(expected)
        val actual = generalRequestResponseSerializer.decodeFromString<CreateChannelRequest>(encodeToString)

        assertEquals(expected, actual)
    }
}
