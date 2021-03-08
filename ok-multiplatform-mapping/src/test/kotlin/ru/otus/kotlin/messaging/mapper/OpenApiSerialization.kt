package ru.otus.kotlin.messaging.mapper

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import ru.otus.kotlin.messaging.mapper.openapi.generalRequestResponseSerializer
import ru.otus.kotlin.messaging.openapi.channel.models.*
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

    @Test
    fun testResponseStatusSerialization() {
        val expected = ResponseStatus.INTERNAL_SERVER_ERROR
        val encodeToString = generalRequestResponseSerializer.encodeToString(expected)
        val actual = generalRequestResponseSerializer.decodeFromString<ResponseStatus>(encodeToString)
        assertEquals(expected, actual)
    }

    @Test
    fun testErrorSeveritySerialization() {
        val expected = ErrorSeverity.INFO
        val encodeToString = generalRequestResponseSerializer.encodeToString(expected)
        val actual = generalRequestResponseSerializer.decodeFromString<ErrorSeverity>(encodeToString)
        assertEquals(expected, actual)
    }

    @Test
    fun testErrorDtoSerialization() {

        val expected = ErrorDto(
            code = "666",
            level = ErrorSeverity.INFO,
            message = "message"
        )

        val encodeToString = generalRequestResponseSerializer.encodeToString(expected)
        val actual = generalRequestResponseSerializer.decodeFromString<ErrorDto>(encodeToString)
        assertEquals(expected, actual)
    }

    @Test
    fun testListErrorDtoSerialization() {

        val expected = listOf(
            ErrorDto(
                code = "666",
                level = ErrorSeverity.INFO,
                message = "info message"
            ),
            ErrorDto(
                code = "777",
                level = ErrorSeverity.FATAL,
                message = "fatal message"
            ),
        )

        val encodeToString = generalRequestResponseSerializer.encodeToString(expected)
        val actual = generalRequestResponseSerializer.decodeFromString<List<ErrorDto>>(encodeToString)
        assertEquals(expected, actual)
    }

    @Test
    fun testCreateChannelResponseSerialization() {

        val channel = ChannelDto(
            id = UUID.randomUUID().toString(),
            name = "channelName",
            ownerId = UUID.randomUUID().toString(),
            type = ChannelType.PUBLIC_CHANNEL
        )

        val request = CreateChannelRequest(
            type = "CreateChannelRequest",
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            channel = channel
        )

        val expected = CreateChannelResponse(
            type = "CreateChannelResponse",
            responseId = UUID.randomUUID().toString(),
            responseTime = LocalDateTime.now().toString(),
            errors = listOf(
                ErrorDto(
                    code = "666",
                    level = ErrorSeverity.INFO,
                    message = "info message"
                ),
                ErrorDto(
                    code = "777",
                    level = ErrorSeverity.FATAL,
                    message = "fatal message"
                ),
            ),
            status = ResponseStatus.BAD_REQUEST,
            request = request,
            channel = channel
        )

        val encodeToString = generalRequestResponseSerializer.encodeToString(expected)
        val actual = generalRequestResponseSerializer.decodeFromString<CreateChannelResponse>(encodeToString)

        assertEquals(expected, actual)
    }
}
