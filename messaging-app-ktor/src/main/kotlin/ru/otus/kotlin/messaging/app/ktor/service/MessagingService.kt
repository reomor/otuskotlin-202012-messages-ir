package ru.otus.kotlin.messaging.app.ktor.service

import ru.otus.kotlin.messaging.api.model.common.Response
import ru.otus.kotlin.messaging.api.model.message.*
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageDto
import ru.otus.kotlin.messaging.mapper.context.TransportContext
import ru.otus.kotlin.messaging.mapper.context.setRequest

class MessagingService {

    suspend fun create(request: CreateChannelMessageRequest): Response = TransportContext().run {
        commonContext.request = request
        messagingContext.setRequest(request)
        createChannelMessageResponse.copy(request = request)
    }

    suspend fun delete(request: DeleteChannelMessageRequest): Response = TransportContext().run {
        commonContext.request = request
        messagingContext.setRequest(request)
        deleteChannelMessageResponse.copy(request = request)
    }

    suspend fun edit(request: EditChannelMessageRequest): Response = TransportContext().run {
        commonContext.request = request
        messagingContext.setRequest(request)
        editChannelMessageResponse.copy(request = request)
    }

    suspend fun get(request: GetChannelMessageRequest): Response = TransportContext().run {
        commonContext.request = request
        messagingContext.setRequest(request)
        getChannelMessageResponse.copy(request = request)
    }

    companion object {

        val channelMessageDto = ChannelMessageDto(
            profileIdFrom = "d6a3577b-395a-4772-ba46-77ce6290a991",
            profileIdTo = "62b80aff-1e25-4726-bc9e-b64d509cae74",
            messageText = "Text message1"
        )

        val createChannelMessageResponse = CreateChannelMessageResponse(
            responseId = "853f7a05-2292-446d-bd13-050c43bfc9df",
            responseTime = "2021-03-21T18:16:55.351733200"
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
            data = listOf(channelMessageDto)
        )
    }
}
