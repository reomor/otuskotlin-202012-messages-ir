package ru.otus.kotlin.messaging.app.spring.controller

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import ru.otus.kotlin.messaging.app.spring.ChannelApi
import ru.otus.kotlin.messaging.app.spring.app
import ru.otus.kotlin.messaging.openapi.channel.models.*
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ChannelControllerTest {

    private val client = WebTestClient.bindToServer()
        .baseUrl("http://localhost:8181")
        .exchangeStrategies(strategies)
        .responseTimeout(Duration.ofSeconds(30000))
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
    fun createChannel() {

        val channel = ChannelDto(
            id = UUID.randomUUID().toString(),
            name = "channelName",
            ownerId = UUID.randomUUID().toString(),
            type = ChannelType.PUBLIC_CHANNEL
        )
        val request: BaseMessage = CreateChannelRequest(
            type = "CreateChannelRequest",
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            channel = channel
        )

        val responseBody = client
            .post()
            .uri(ChannelApi.createChannelUri)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(request)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<CreateChannelResponse>()
            .returnResult()
            .responseBody

        assertEquals(channel, responseBody?.channel)
        assertEquals(request, responseBody?.request)
    }

    @Test
    fun deleteChannel() {

        val request: BaseMessage = DeleteChannelRequest(
            type = "CreateChannelRequest",
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            channelId = UUID.randomUUID().toString()
        )

        val responseBody = client
            .post()
            .uri(ChannelApi.deleteChannelUri)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(request)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<DeleteChannelResponse>()
            .returnResult()
            .responseBody

        assertEquals(request, responseBody?.request)
    }

    @Test
    fun getChannel() {

        val request: BaseMessage = GetChannelRequest(
            type = "CreateChannelRequest",
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            filter = ChannelFilterDto(
                channelIds = listOf(
                    UUID.randomUUID().toString()
                )
            )
        )

        val responseBody = client
            .post()
            .uri(ChannelApi.getChannelUri)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(request)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<GetChannelResponse>()
            .returnResult()
            .responseBody

        assertEquals(request, responseBody?.request)
    }
}
