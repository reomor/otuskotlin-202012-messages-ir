package ru.otus.kotlin.messaging.app.spring.controller

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.http.codec.json.KotlinSerializationJsonEncoder
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.web.reactive.function.client.ExchangeStrategies
import ru.otus.kotlin.messaging.api.model.common.AbstractRequest
import ru.otus.kotlin.messaging.api.model.common.Request
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageResponse
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageDto
import ru.otus.kotlin.messaging.api.model.message.serialization.requestResponseSerializer
import ru.otus.kotlin.messaging.app.spring.MessagingApi
import ru.otus.kotlin.messaging.app.spring.app
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

//@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MessagingControllerTest {

    // Polymorphic serialization
    private var strategies = ExchangeStrategies.builder()
        .codecs { clientDefaultCodecsConfigurer: ClientCodecConfigurer ->
            clientDefaultCodecsConfigurer.defaultCodecs()
                .kotlinSerializationJsonEncoder(KotlinSerializationJsonEncoder(requestResponseSerializer))
            clientDefaultCodecsConfigurer.defaultCodecs()
                .kotlinSerializationJsonDecoder(KotlinSerializationJsonDecoder(requestResponseSerializer))
            clientDefaultCodecsConfigurer.defaultCodecs()
        }
        .build()

    private val client = WebTestClient.bindToServer()
        .baseUrl("http://localhost:8181")
        .exchangeStrategies(strategies)
        .responseTimeout(Duration.ofSeconds(3))
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
    fun create() {

        val request: Request = CreateChannelMessageRequest(
            requestId = UUID.randomUUID().toString(),
            requestTime = LocalDateTime.now().toString(),
            data = ChannelMessageDto(
                profileIdFrom = UUID.randomUUID().toString(),
                profileIdTo = UUID.randomUUID().toString(),
                messageText = "Text"
            )
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
}