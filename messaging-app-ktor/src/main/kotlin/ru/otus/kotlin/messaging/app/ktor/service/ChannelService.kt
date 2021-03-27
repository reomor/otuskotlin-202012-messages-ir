package ru.otus.kotlin.messaging.app.ktor.service

import ru.otus.kotlin.messaging.business.backend.ChannelPipelineService
import ru.otus.kotlin.messaging.mapper.context.ContextStubCase
import ru.otus.kotlin.messaging.mapper.context.TransportContext
import ru.otus.kotlin.messaging.mapper.context.fromContext
import ru.otus.kotlin.messaging.mapper.context.setRequest
import ru.otus.kotlin.messaging.openapi.channel.models.*
import ru.otus.kotlin.messaging.openapi.channel.models.ChannelType.PUBLIC_CHANNEL

class ChannelService(
    private val channelPipelineService: ChannelPipelineService
) {
    suspend fun create(request: CreateChannelRequest): BaseMessage = TransportContext().run {
        setRequest(request)
        channelPipelineService.create(this)
        CreateChannelResponse().fromContext(this)
    }

    suspend fun delete(request: DeleteChannelRequest): BaseMessage = TransportContext().run {
        setRequest(request)
        channelPipelineService.delete(this)
        DeleteChannelResponse().fromContext(this)
    }

    suspend fun get(request: GetChannelRequest): BaseMessage = TransportContext().run {
        setRequest(request)
        channelPipelineService.get(this)
        GetChannelResponse().fromContext(this)
    }
}
