package ru.otus.kotlin.messaging.business.backend.operation.stub

import ru.otus.kotlin.messaging.*
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageResponse
import ru.otus.kotlin.messaging.api.model.message.DeleteChannelMessageResponse
import ru.otus.kotlin.messaging.api.model.message.EditChannelMessageResponse
import ru.otus.kotlin.messaging.api.model.message.GetChannelMessageResponse
import ru.otus.kotlin.messaging.mapper.context.toDto
import ru.otus.kotlin.messaging.mapper.context.toMessageDto
import ru.otus.kotlin.messaging.openapi.channel.models.CreateChannelResponse
import ru.otus.kotlin.messaging.openapi.channel.models.DeleteChannelResponse
import ru.otus.kotlin.messaging.openapi.channel.models.GetChannelResponse
import ru.otus.kotlin.messaging.openapi.channel.models.ResponseStatus

object MessageStubs {

    val createChannelMessageResponse = CreateChannelMessageResponse(
        responseId = "853f7a05-2292-446d-bd13-050c43bfc9df",
        responseTime = "2021-03-21T18:16:55.351733200"
    )

    val channelMessageStub = InstantMessage(
        profileIdFrom = ProfileId("d6a3577b-395a-4772-ba46-77ce6290a991"),
        profileIdTo = ProfileId("62b80aff-1e25-4726-bc9e-b64d509cae74"),
        messageText = "Text message1"
    )

    val deleteChannelMessageResponse = DeleteChannelMessageResponse(
        responseId = "853f7a05-2292-446d-bd13-050c43bfc9df",
        responseTime = "2021-03-21T18:16:55.351733200"
    )

    val editChannelMessageResponse = EditChannelMessageResponse(
        responseId = "853f7a05-2292-446d-bd13-050c43bfc9df",
        responseTime = "2021-03-21T18:16:55.351733200"
    )

    val getChannelMessageResponse = GetChannelMessageResponse(
        responseId = "853f7a05-2292-446d-bd13-050c43bfc9df",
        responseTime = "2021-03-21T18:16:55.351733200",
        data = listOf(channelMessageStub.toMessageDto())
    )
}

object ChannelStubs {

    val channelStub = Channel(
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
        channel = channelStub.toDto()
    )

    val deleteChannelResponse = DeleteChannelResponse(
        type = "DeleteChannelResponse",
        responseId = "853f7a05-2292-446d-bd13-050c43bfc9df",
        responseTime = "2021-03-21T18:16:55.351733200",
        errors = emptyList(),
        status = ResponseStatus.SUCCESS,
        channel = channelStub.toDto()
    )

    val getChannelResponse = GetChannelResponse(
        type = "GetChannelResponse",
        responseId = "853f7a05-2292-446d-bd13-050c43bfc9df",
        responseTime = "2021-03-21T18:16:55.351733200",
        errors = emptyList(),
        status = ResponseStatus.SUCCESS,
        channels = listOf(channelStub.toDto())
    )
}
