package ru.otus.kotlin.messaging.api.model.message

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.otus.kotlin.messaging.api.model.common.AbstractRequest
import ru.otus.kotlin.messaging.api.model.common.AbstractResponse
import ru.otus.kotlin.messaging.api.model.common.Request
import ru.otus.kotlin.messaging.api.model.common.Response
import ru.otus.kotlin.messaging.api.model.common.dto.DebugMode.TEST
import ru.otus.kotlin.messaging.api.model.message.dto.PersonalMessageDto
import ru.otus.kotlin.messaging.api.model.message.serialization.requestResponseSerializer
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CreatePersonalMessageTest {

    @Test
    fun requestSerializationDeserialization() {

        val expectedRequest = CreatePersonalMessageRequest(
            requestId = "123456789abc",
            requestTime = "2021-02-23T12:00:00",
            debug = CreatePersonalMessageRequest.DebugDto(TEST),
            data = PersonalMessageDto(
                "1",
                "2",
                "Test message"
            )
        )

        val actualRequest = Json.decodeFromString<CreatePersonalMessageRequest>(
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

        val expectedRequest: AbstractRequest = CreatePersonalMessageRequest(
            requestId = "123456789abc",
            requestTime = "2021-02-23T12:00:00",
            debug = CreatePersonalMessageRequest.DebugDto(TEST),
            data = PersonalMessageDto(
                "1",
                "2",
                "Test message"
            )
        )

        val expectedResponse = CreatePersonalMessageResponse(
            responseId = "cba987654321",
            responseTime = "2021-02-23T13:00:00",
            request = expectedRequest
        )

        val encodeToString = requestResponseSerializer.encodeToString(expectedResponse)
        val actualResponse = requestResponseSerializer.decodeFromString<CreatePersonalMessageResponse>(encodeToString)

        assertEquals(
            expected = expectedResponse,
            actual = actualResponse,
            message = "Contents must be equal"
        )
    }

    @Test
    fun polymorphAbstractClassSerializationDeserialization() {

        val expectedRequest: AbstractRequest = CreatePersonalMessageRequest(
            requestId = "123456789abc",
            requestTime = "2021-02-23T12:00:00",
            debug = CreatePersonalMessageRequest.DebugDto(TEST),
            data = PersonalMessageDto(
                "1",
                "2",
                "Test message"
            )
        )

        val expectedResponse: AbstractResponse = CreatePersonalMessageResponse(
            responseId = "cba987654321",
            responseTime = "2021-02-23T13:00:00",
            request = expectedRequest
        )

        val encodeToString = requestResponseSerializer.encodeToString(expectedResponse)
        val response: AbstractResponse = requestResponseSerializer.decodeFromString(AbstractResponse.serializer(), encodeToString)

        assertEquals(
            (expectedResponse as? CreatePersonalMessageResponse),
            (response as? CreatePersonalMessageResponse)
        )
    }

//    Doesn't work for JS
//    @Test
    fun polymorphInterfaceSerializationDeserialization() {

        val expectedRequest: Request = CreatePersonalMessageRequest(
            requestId = "123456789abc",
            requestTime = "2021-02-23T12:00:00",
            debug = CreatePersonalMessageRequest.DebugDto(TEST),
            data = PersonalMessageDto(
                "1",
                "2",
                "Test message"
            )
        )

        val expectedResponse: Response = CreatePersonalMessageResponse(
            responseId = "cba987654321",
            responseTime = "2021-02-23T13:00:00",
            request = expectedRequest,
            debug = CreatePersonalMessageResponse.DebugDto(TEST)
        )

        val encodeToString = requestResponseSerializer.encodeToString(expectedResponse)
        val response: Response = requestResponseSerializer.decodeFromString(encodeToString)

        assertEquals(
            (expectedResponse as? CreatePersonalMessageResponse),
            (response as? CreatePersonalMessageResponse)
        )
    }
}
