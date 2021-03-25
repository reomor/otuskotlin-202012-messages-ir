package ru.otus.kotlin.messaging.business.backend.operation.stub

import ru.otus.kotlin.messaging.Channel
import ru.otus.kotlin.messaging.ChannelId
import ru.otus.kotlin.messaging.ChannelType
import ru.otus.kotlin.messaging.ProfileId
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageResponse
import ru.otus.kotlin.messaging.mapper.context.toDto
import ru.otus.kotlin.messaging.openapi.channel.models.CreateChannelResponse
import ru.otus.kotlin.messaging.openapi.channel.models.ErrorDto
import ru.otus.kotlin.messaging.openapi.channel.models.ResponseStatus

object Stubs {

    val createChannelMessageResponse = CreateChannelMessageResponse(
        responseId = "853f7a05-2292-446d-bd13-050c43bfc9df",
        responseTime = "2021-03-21T18:16:55.351733200"
    )

    val channel = Channel(
        channelId = ChannelId("d992d2f5-e6ed-4172-99a6-012dd118e634"),
        name = "channelName",
        ownerId = ProfileId("ade08ffd-d670-4e13-b1a9-f211672522d1"),
        type = ChannelType.PUBLIC_CHANNEL
    )

    val createChannelResponse = CreateChannelResponse(
        type = "CreateChannelResponse",
        responseId = "853f7a05-2292-446d-bd13-050c43bfc9df",
        responseTime = "2021-03-21T18:16:55.351733200",
        errors = emptyList(),
        status = ResponseStatus.SUCCESS,
        channel = channel.toDto()
    )
}
