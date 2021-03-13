package ru.otus.kotlin.messaging.mapper

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.junit.jupiter.api.Test
import ru.otus.kotlin.messaging.mapper.openapi.generalRequestResponseSerializer
import ru.otus.kotlin.messaging.openapi.channel.models.*
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals

internal class OpenApiSerialization {

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
    fun testChannelFilterDtoDtoSerialization() {

        val expected = ChannelFilterDto(
            channelIds = listOf(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
            ),
            pageSize = 10,
            pageNumber = 0
        )

        val encodeToString = generalRequestResponseSerializer.encodeToString(expected)
        val actual = generalRequestResponseSerializer.decodeFromString<ChannelFilterDto>(encodeToString)
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

    @Test
    fun testErrorCreateChannelResponseSerialization() {

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
            request = request
        )

        val encodeToString = generalRequestResponseSerializer.encodeToString(expected)
        val actual = generalRequestResponseSerializer.decodeFromString<CreateChannelResponse>(encodeToString)

        assertEquals(expected, actual)
    }

    @Test
    fun testErrorCreateChannelResponseSerialization() {

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
            request = request
        )

        val encodeToString = generalRequestResponseSerializer.encodeToString(expected)
        val actual = generalRequestResponseSerializer.decodeFromString<CreateChannelResponse>(encodeToString)

        assertEquals(expected, actual)
    }

    @Test
    fun testDeleteChannelRequestSerialization() {

        // doesn't matter polymorphic doesn't work
        val expected: BaseMessage = DeleteChannelRequest(
            type = "DeleteChannelRequest",
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            channelId = UUID.randomUUID().toString(),
        )

        val encodeToString = generalRequestResponseSerializer.encodeToString(expected)
        val actual = generalRequestResponseSerializer.decodeFromString<DeleteChannelRequest>(encodeToString)

        assertEquals(expected, actual)
    }

    @Test
    fun testDeleteChannelResponseSerialization() {

        val channel = ChannelDto(
            id = UUID.randomUUID().toString(),
            name = "channelName",
            ownerId = UUID.randomUUID().toString(),
            type = ChannelType.PUBLIC_CHANNEL
        )

        val request = DeleteChannelRequest(
            type = "DeleteChannelRequest",
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            channelId = UUID.randomUUID().toString(),
        )

        val expected = DeleteChannelResponse(
            type = "DeleteChannelResponse",
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
        val actual = generalRequestResponseSerializer.decodeFromString<DeleteChannelResponse>(encodeToString)

        assertEquals(expected, actual)
    }

    @Test
    fun testErrorDeleteChannelResponseSerialization() {

        val request = DeleteChannelRequest(
            type = "DeleteChannelRequest",
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            channelId = UUID.randomUUID().toString(),
        )

        val expected = DeleteChannelResponse(
            type = "DeleteChannelResponse",
            responseId = UUID.randomUUID().toString(),
            responseTime = LocalDateTime.now().toString(),
            errors = listOf(
                ErrorDto(
                    code = "666",
                    level = ErrorSeverity.INFO,
                    message = "info message"
                )
            ),
            status = ResponseStatus.BAD_REQUEST,
            request = request
        )

        val encodeToString = generalRequestResponseSerializer.encodeToString(expected)
        val actual = generalRequestResponseSerializer.decodeFromString<DeleteChannelResponse>(encodeToString)

        assertEquals(expected, actual)
    }

    @Test
    fun testGetChannelRequestSerialization() {

        val filter = ChannelFilterDto(
            channelIds = listOf(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
            ),
            pageSize = 10,
            pageNumber = 0
        )

        val expected = GetChannelRequest(
            type = "GetChannelRequest",
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            filter = filter
        )

        val encodeToString = generalRequestResponseSerializer.encodeToString(expected)
        val actual = generalRequestResponseSerializer.decodeFromString<GetChannelRequest>(encodeToString)

        assertEquals(expected, actual)
    }

    @Test
    fun testGetChannelResponseSerialization() {

        val filter = ChannelFilterDto(
            channelIds = listOf(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
            ),
            pageSize = 10,
            pageNumber = 0
        )

        val request = GetChannelRequest(
            type = "GetChannelRequest",
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            filter = filter
        )

        val expected = GetChannelResponse(
            type = "GetChannelResponse",
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
            channels = listOf(
                ChannelDto(
                    id = UUID.randomUUID().toString(),
                    name = "channelName",
                    ownerId = UUID.randomUUID().toString(),
                    type = ChannelType.PUBLIC_CHANNEL
                )
            )
        )

        val encodeToString = generalRequestResponseSerializer.encodeToString(expected)
        val actual = generalRequestResponseSerializer.decodeFromString<GetChannelResponse>(encodeToString)

        assertEquals(expected, actual)
    }
}
