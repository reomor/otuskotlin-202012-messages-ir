package ru.otus.kotlin.messaging.api.model.message

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import ru.otus.kotlin.messaging.api.model.common.Request
import ru.otus.kotlin.messaging.api.model.common.dto.DebugDto
import ru.otus.kotlin.messaging.api.model.common.dto.DebugMode.TEST
import ru.otus.kotlin.messaging.api.model.message.dto.PersonalMessageDto
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CreatePersonalMessageTest {

    @Test
    fun `request serialization and deserialization`() {
        val expectedRequest = CreatePersonalMessageRequest(
            requestId = "123456789abc",
            requestTime = "2021-02-23T12:00:00",
            debug = DebugDto(TEST),
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
    fun `response serialization and deserialization`() {
        val expectedRequest = CreatePersonalMessageRequest(
            requestId = "123456789abc",
            requestTime = "2021-02-23T12:00:00",
            debug = DebugDto(TEST),
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

        val json = Json {
            serializersModule = SerializersModule {
                polymorphic(Request::class) {
                    subclass(CreatePersonalMessageRequest::class, CreatePersonalMessageRequest.serializer())
                }
            }
        }

        val actualResponse = json.decodeFromString<CreatePersonalMessageResponse>(
            json.encodeToString(expectedResponse)
        )

        assertEquals(
            expected = expectedResponse,
            actual = actualResponse,
            message = "Contents must be equal"
        )
    }
}
