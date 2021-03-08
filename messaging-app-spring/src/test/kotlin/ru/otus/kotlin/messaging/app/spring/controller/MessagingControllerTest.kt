package ru.otus.kotlin.messaging.app.spring.controller

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import ru.otus.kotlin.messaging.api.model.common.Request
import ru.otus.kotlin.messaging.api.model.message.*
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageDto
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageFilter
import ru.otus.kotlin.messaging.app.spring.MessagingApi
import ru.otus.kotlin.messaging.app.spring.app
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MessagingControllerTest {

    private val client = WebTestClient.bindToServer()
        .baseUrl("http://localhost:8181")
        .exchangeStrategies(strategies)
        .responseTimeout(Duration.ofSeconds(300))
        .build()

    private lateinit var context: ConfigurableApplicationContext

    @BeforeAll
    fun beforeAll() {
        context = app.run(profiles = "test")
    }

    @AfterAll
    fun afterAll() {
        context.close()
    }

    @Test
    fun createInstantMessage() {

        val request: Request = CreateChannelMessageRequest(
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            data = stubChannelMessage()
        )

        val responseBody = client
            .post()
            .uri(MessagingApi.createMessageUri)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(request)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<CreateChannelMessageResponse>()
            .returnResult()
            .responseBody

        assertEquals(request, responseBody?.request)
    }

    @Test
    fun createChannelMessage() {

        val request: Request = CreateChannelMessageRequest(
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            data = stubChannelMessage()
        )

        val responseBody = client
            .post()
            .uri(MessagingApi.createMessageUri)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(request)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<CreateChannelMessageResponse>()
            .returnResult()
            .responseBody

        assertEquals(request, responseBody?.request)
    }

    @Test
    fun deleteMessage() {

        val request: Request = DeleteChannelMessageRequest(
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            messageId = UUID.randomUUID().toString(),
            channelId = UUID.randomUUID().toString()
        )

        val responseBody = client
            .post()
            .uri(MessagingApi.deleteMessageUri)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(request)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<DeleteChannelMessageResponse>()
            .returnResult()
            .responseBody

        assertEquals(request, responseBody?.request)
    }

    @Test
    fun editMessage() {

        val request: Request = EditChannelMessageRequest(
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            messageId = UUID.randomUUID().toString(),
            channelId = UUID.randomUUID().toString(),
            data = ChannelMessageDto(
                profileIdFrom = UUID.randomUUID().toString(),
                channelId = UUID.randomUUID().toString(),
                messageText = "Text"
            )
        )

        val responseBody = client
            .post()
            .uri(MessagingApi.editMessageUri)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(request)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<EditChannelMessageResponse>()
            .returnResult()
            .responseBody

        assertEquals(request, responseBody?.request)
    }

    @Test
    fun getMessages() {

        val request: Request = GetChannelMessageRequest(
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            filter = ChannelMessageFilter(
                profileIdFrom = UUID.randomUUID().toString(),
                channelId = UUID.randomUUID().toString(),
                pageNumber = 0,
                pageSize = 100
            )
        )

        val responseBody = client
            .post()
            .uri(MessagingApi.getMessageUri)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(request)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<GetChannelMessageResponse>()
            .returnResult()
            .responseBody

        assertFalse(responseBody?.data?.isEmpty() ?: true)
        assertEquals(request, responseBody?.request)
    }

    private fun stubChannelMessage() = ChannelMessageDto(
        profileIdFrom = UUID.randomUUID().toString(),
        channelId = UUID.randomUUID().toString(),
        messageText = "Text"
    )
}
