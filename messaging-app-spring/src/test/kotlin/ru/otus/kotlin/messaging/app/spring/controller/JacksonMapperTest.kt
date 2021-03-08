package ru.otus.kotlin.messaging.app.spring.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import ru.otus.kotlin.messaging.openapi.channel.models.ChannelDto
import ru.otus.kotlin.messaging.openapi.channel.models.ChannelType
import ru.otus.kotlin.messaging.openapi.channel.models.CreateChannelRequest
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals

class JacksonMapperTest {

    private val mapper = ObjectMapper().registerModule(KotlinModule())

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

        val encodeToString = mapper.writeValueAsString(expected)
        val actual: CreateChannelRequest = mapper.readValue(encodeToString)

        assertEquals(expected, actual)
    }
}
