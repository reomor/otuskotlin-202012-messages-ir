package ru.otus.kotlin.messaging.app.ktor

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.routing.*
import io.ktor.serialization.*
import ru.otus.kotlin.messaging.app.ktor.controller.channelRoute
import ru.otus.kotlin.messaging.app.ktor.controller.messageRoute
import ru.otus.kotlin.messaging.app.ktor.service.ChannelService
import ru.otus.kotlin.messaging.app.ktor.service.MessagingService
import ru.otus.kotlin.messaging.business.backend.ChannelPipelineService
import ru.otus.kotlin.messaging.business.backend.MessagePipelineService
import ru.otus.kotlin.messaging.mapper.openapi.generalRequestResponseSerializer

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    val channelPipelineService = ChannelPipelineService()
    val messagePipelineService = MessagePipelineService()

    val messagingService = MessagingService(messagePipelineService)
    val channelService = ChannelService(channelPipelineService)

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(ContentNegotiation) {
        json(
            contentType = ContentType.Application.Json,
            json = generalRequestResponseSerializer
        )
    }

    routing {
        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }

        messageRoute(messagingService)
        channelRoute(channelService)
    }
}

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)
