package ru.otus.kotlin.messaging.app.spring.controller

import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.http.codec.json.KotlinSerializationJsonEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import ru.otus.kotlin.messaging.mapper.openapi.generalRequestResponseSerializer

// Polymorphic serialization
var strategies = ExchangeStrategies.builder()
    .codecs { clientDefaultCodecsConfigurer: ClientCodecConfigurer ->
        clientDefaultCodecsConfigurer.defaultCodecs()
            .kotlinSerializationJsonEncoder(KotlinSerializationJsonEncoder(generalRequestResponseSerializer))
        clientDefaultCodecsConfigurer.defaultCodecs()
            .kotlinSerializationJsonDecoder(KotlinSerializationJsonDecoder(generalRequestResponseSerializer))
    }
    .build()
