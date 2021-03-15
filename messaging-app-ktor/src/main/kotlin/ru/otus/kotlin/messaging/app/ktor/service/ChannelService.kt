package ru.otus.kotlin.messaging.app.ktor.service

import ru.otus.kotlin.messaging.Channel
import ru.otus.kotlin.messaging.ChannelId
import ru.otus.kotlin.messaging.ChannelType
import ru.otus.kotlin.messaging.ProfileId
import ru.otus.kotlin.messaging.mapper.context.TransportContext
import ru.otus.kotlin.messaging.mapper.context.fromContext
import ru.otus.kotlin.messaging.mapper.context.setRequest
import ru.otus.kotlin.messaging.openapi.channel.models.BaseMessage
import ru.otus.kotlin.messaging.openapi.channel.models.CreateChannelRequest
import ru.otus.kotlin.messaging.openapi.channel.models.CreateChannelResponse
import java.util.*

class ChannelService {

    suspend fun create(request: CreateChannelRequest): BaseMessage = TransportContext().run {
        setRequest(request)
        messagingContext.channel = Channel(
            channelId = ChannelId(UUID.randomUUID().toString()),
            name = "channelName",
            ownerId = ProfileId(UUID.randomUUID().toString()),
            type = ChannelType.PUBLIC_CHANNEL
        )

        CreateChannelResponse().fromContext(this)
    }
}