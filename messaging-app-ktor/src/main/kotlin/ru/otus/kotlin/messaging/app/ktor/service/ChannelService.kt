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
        stubCase = ContextStubCase.CHANNEL_CREATE_SUCCESS
        setRequest(request)
        channelPipelineService.create(this)
        CreateChannelResponse().fromContext(this)
    }

    suspend fun delete(request: DeleteChannelRequest): BaseMessage = TransportContext().run {
        setRequest(request)
        deleteChannelResponse.copy(request = request)
    }

    suspend fun get(request: GetChannelRequest): BaseMessage = TransportContext().run {
        setRequest(request)
        getChannelResponse.copy(request = request)
    }

    companion object {

        val channelDto = ChannelDto(
            id = "d992d2f5-e6ed-4172-99a6-012dd118e634",
            name = "channelName",
            ownerId = "ade08ffd-d670-4e13-b1a9-f211672522d1",
            type = PUBLIC_CHANNEL
        )

        val createChannelResponse = CreateChannelResponse(
            type = "CreateChannelResponse",
            responseId = "853f7a05-2292-446d-bd13-050c43bfc9df",
            responseTime = "2021-03-21T18:16:55.351733200",
            status = ResponseStatus.SUCCESS,
            channel = channelDto
        )

        val deleteChannelResponse = DeleteChannelResponse(
            type = "DeleteChannelResponse",
            responseId = "853f7a05-2292-446d-bd13-050c43bfc9df",
            responseTime = "2021-03-21T18:16:55.351733200",
            status = ResponseStatus.SUCCESS,
            channel = channelDto
        )

        val getChannelResponse = GetChannelResponse(
            type = "GetChannelResponse",
            responseId = "853f7a05-2292-446d-bd13-050c43bfc9df",
            responseTime = "2021-03-21T18:16:55.351733200",
            status = ResponseStatus.SUCCESS,
            channels = listOf(channelDto)
        )
    }
}