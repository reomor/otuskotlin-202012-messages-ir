package ru.otus.kotlin.messaging.mapper

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.otus.kotlin.messaging.api.model.common.dto.CommonResponseStatus
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageResponse
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageDto
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
                message = "Hello World"
            )
        )

        context.commonContext.request = request

        val response = CreateChannelMessageResponse(
            responseId = UUID.randomUUID().toString(),
            responseTime = LocalDateTime.now().toString()
        ).toDto(context)

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
}