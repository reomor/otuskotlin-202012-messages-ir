package ru.otus.kotlin.messaging.app.ktor.service

import ru.otus.kotlin.messaging.api.model.common.Response
import ru.otus.kotlin.messaging.api.model.message.*
import ru.otus.kotlin.messaging.business.backend.MessagePipelineService
import ru.otus.kotlin.messaging.mapper.context.TransportContext
import ru.otus.kotlin.messaging.mapper.context.fromContext
import ru.otus.kotlin.messaging.mapper.context.setRequest

class MessagingService(
    private val messagePipelineService: MessagePipelineService
) {
    suspend fun create(request: CreateChannelMessageRequest): Response = TransportContext().run {
        setRequest(request)
        messagePipelineService.create(this)
        CreateChannelMessageResponse().fromContext(this)
    }

    suspend fun delete(request: DeleteChannelMessageRequest): Response = TransportContext().run {
        setRequest(request)
        messagePipelineService.delete(this)
        DeleteChannelMessageResponse().fromContext(this)
    }

    suspend fun edit(request: EditChannelMessageRequest): Response = TransportContext().run {
        setRequest(request)
        messagePipelineService.edit(this)
        EditChannelMessageResponse().fromContext(this)
    }

    suspend fun get(request: GetChannelMessageRequest): Response = TransportContext().run {
        setRequest(request)
        messagePipelineService.get(this)
        GetChannelMessageResponse().fromContext(this)
    }
}
