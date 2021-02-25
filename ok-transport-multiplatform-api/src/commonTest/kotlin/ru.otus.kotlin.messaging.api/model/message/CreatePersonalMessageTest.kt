package ru.otus.kotlin.messaging.api.model.message

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.otus.kotlin.messaging.api.model.common.AbstractRequest
import ru.otus.kotlin.messaging.api.model.common.AbstractResponse
import ru.otus.kotlin.messaging.api.model.common.Request
import ru.otus.kotlin.messaging.api.model.common.Response
import ru.otus.kotlin.messaging.api.model.common.dto.DebugMode.TEST
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageDto
import ru.otus.kotlin.messaging.api.model.message.serialization.requestResponseSerializer
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CreatePersonalMessageTest {

    @Test
    fun requestSerializationDeserialization() {

        val expectedRequest = CreateChannelMessageRequest(
            requestId = "123456789abc",
            requestTime = "2021-02-23T12:00:00",
            debug = CreateChannelMessageRequest.DebugDto(TEST),
            data = ChannelMessageDto(
                "1",
                "2",
                "Test message"
            )
        )

        val actualRequest = Json.decodeFromString<CreateChannelMessageRequest>(
            Json.encodeToString(expectedRequest)
        )

        assertEquals(
            expected = expectedRequest,
            actual = actualRequest,
            message = "Contents must be equal"
        )
    }

    @Test
    fun responseSerializationDeserialization() {

        val expectedRequest: AbstractRequest = CreateChannelMessageRequest(
            requestId = "123456789abc",
            requestTime = "2021-02-23T12:00:00",
            debug = CreateChannelMessageRequest.DebugDto(TEST),
            data = ChannelMessageDto(
                "1",
                "2",
                "Test message"
            )
        )

        val expectedResponse = CreateChannelMessageResponse(
            responseId = "cba987654321",
            responseTime = "2021-02-23T13:00:00",
            request = expectedRequest
        )

        val encodeToString = requestResponseSerializer.encodeToString(expectedResponse)
        val actualResponse = requestResponseSerializer.decodeFromString<CreateChannelMessageResponse>(encodeToString)

        assertEquals(
            expected = expectedResponse,
            actual = actualResponse,
            message = "Contents must be equal"
        )
    }

    @Test
    fun polymorphAbstractClassSerializationDeserialization() {

        val expectedRequest: AbstractRequest = CreateChannelMessageRequest(
            requestId = "123456789abc",
            requestTime = "2021-02-23T12:00:00",
            debug = CreateChannelMessageRequest.DebugDto(TEST),
            data = ChannelMessageDto(
                "1",
                "2",
                "Test message"
            )
        )

        val expectedResponse: AbstractResponse = CreateChannelMessageResponse(
            responseId = "cba987654321",
            responseTime = "2021-02-23T13:00:00",
            request = expectedRequest
        )

        val encodeToString = requestResponseSerializer.encodeToString(expectedResponse)
        val response: AbstractResponse = requestResponseSerializer.decodeFromString(AbstractResponse.serializer(), encodeToString)

        assertEquals(
            (expectedResponse as? CreateChannelMessageResponse),
            (response as? CreateChannelMessageResponse)
        )
    }

//    Doesn't work for JS
//    @Test
    fun polymorphInterfaceSerializationDeserialization() {

        val expectedRequest: Request = CreateChannelMessageRequest(
            requestId = "123456789abc",
            requestTime = "2021-02-23T12:00:00",
            debug = CreateChannelMessageRequest.DebugDto(TEST),
            data = ChannelMessageDto(
                "1",
                "2",
                "Test message"
            )
        )

        val expectedResponse: Response = CreateChannelMessageResponse(
            responseId = "cba987654321",
            responseTime = "2021-02-23T13:00:00",
            request = expectedRequest,
            debug = CreateChannelMessageResponse.DebugDto(TEST)
        )

        val encodeToString = requestResponseSerializer.encodeToString(expectedResponse)
        val response: Response = requestResponseSerializer.decodeFromString(encodeToString)

        assertEquals(
            (expectedResponse as? CreateChannelMessageResponse),
            (response as? CreateChannelMessageResponse)
        )
    }
}
