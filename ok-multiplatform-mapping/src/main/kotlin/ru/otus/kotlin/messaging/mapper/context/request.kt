package ru.otus.kotlin.messaging.mapper.context

import ru.otus.kotlin.messaging.*
import ru.otus.kotlin.messaging.ChannelType
import ru.otus.kotlin.messaging.api.model.common.Request
import ru.otus.kotlin.messaging.api.model.common.dto.StubCase
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.DeleteChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.EditChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.GetChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageDto
import ru.otus.kotlin.messaging.context.MessagingContext
import ru.otus.kotlin.messaging.mapper.context.ContextStubCase.*
import ru.otus.kotlin.messaging.openapi.channel.models.*
import java.lang.IllegalArgumentException
import java.util.*

inline fun <reified T : Request> TransportContext.setRequest(request: T): TransportContext {
    commonContext.request = request
    messagingContext = MessagingContext()
    when (T::class) {
        CreateChannelMessageRequest::class -> {
            val createChannelMessageRequest = request as CreateChannelMessageRequest
            stubCase = setStub(createChannelMessageRequest.debug?.stubCase, MESSAGE_CREATE_SUCCESS)
            messagingContext.setRequest(createChannelMessageRequest)
        }
        DeleteChannelMessageRequest::class -> {
            val deleteChannelMessageRequest = request as DeleteChannelMessageRequest
            stubCase = setStub(deleteChannelMessageRequest.debug?.stubCase, MESSAGE_DELETE_SUCCESS)
            messagingContext.setRequest(deleteChannelMessageRequest)
        }
        EditChannelMessageRequest::class -> {
            val editChannelMessageRequest = request as EditChannelMessageRequest
            stubCase = setStub(editChannelMessageRequest.debug?.stubCase, MESSAGE_EDIT_SUCCESS)
            messagingContext.setRequest(editChannelMessageRequest)
        }
        GetChannelMessageRequest::class -> {
            val getChannelMessageRequest = request as GetChannelMessageRequest
            stubCase = setStub(getChannelMessageRequest.debug?.stubCase, MESSAGE_GET_SUCCESS)
            messagingContext.setRequest(getChannelMessageRequest)
        }
        else -> throw IllegalArgumentException("Class: ${T::class.simpleName} is not supported")
    }
    return this
}

inline fun <reified T : BaseMessage> TransportContext.setRequest(request: T): TransportContext {
    openApiContext.request = request
    messagingContext = MessagingContext()
    when (T::class) {
        CreateChannelRequest::class -> {
            val createChannelRequest = request as CreateChannelRequest
            stubCase = setStub(createChannelRequest.debug?.stubCase, CHANNEL_CREATE_SUCCESS)
            messagingContext.setRequest(createChannelRequest)
        }
        DeleteChannelRequest::class -> {
            val deleteChannelRequest = request as DeleteChannelRequest
            stubCase = setStub(deleteChannelRequest.debug?.stubCase, CHANNEL_DELETE_SUCCESS)
            messagingContext.setRequest(deleteChannelRequest)
        }
        GetChannelRequest::class -> {
            val getChannelRequest = request as GetChannelRequest
            stubCase = setStub(getChannelRequest.debug?.stubCase, CHANNEL_GET_SUCCESS)
            messagingContext.setRequest(getChannelRequest)
        }
        else -> throw IllegalArgumentException("Class: ${T::class.simpleName} is not supported")
    }
    return this
}

fun MessagingContext.setRequest(request: CreateChannelMessageRequest) {
    request.data?.let { channelMessageDto -> handleMessage(channelMessageDto) }
}

fun MessagingContext.setRequest(request: DeleteChannelMessageRequest) {
    request.messageId?.let { messageId -> this.messageIds = listOf(MessageId(messageId)) }
    request.channelId?.let { channelId -> this.channelId = ChannelId(channelId) }
}

fun MessagingContext.setRequest(request: EditChannelMessageRequest) {
    request.messageId?.let { messageId -> messageIds = listOf(MessageId(messageId)) }
    request.channelId?.let { channelId = ChannelId(it) }
    request.data?.let { channelMessageDto -> handleMessage(channelMessageDto) }
}

fun MessagingContext.setRequest(request: GetChannelMessageRequest) {
    request.filter?.let { filter ->
        filter.channelId?.let { channelId = ChannelId(it) }
        filter.profileIdFrom?.let { profileIdFrom = ProfileId(it) }
        filter.profileIdTo?.let { profileIdTo = ProfileId(it) }
        messageIds = filter.messageIds.map { messageId -> MessageId(messageId) }
        page = Page(filter.pageSize, filter.pageNumber)
    }
}

fun MessagingContext.setRequest(request: CreateChannelRequest) {
    request.channel?.let { channelDto ->
        this.channel = Channel(
            channelId = channelDto.id?.let { ChannelId(it) } ?: ChannelId.NONE,
            name = channelDto.name ?: UUID.randomUUID().toString(),
            ownerId = channelDto.ownerId?.let { ProfileId(it) } ?: ProfileId.NONE,
            type = channelDto.type?.let { ChannelType.valueOf(it.value.toUpperCase()) } ?: ChannelType.UNDEFINED
        )
    }
}

fun MessagingContext.setRequest(request: DeleteChannelRequest) {
    request.channelId?.let { channelId = ChannelId(it) }
}

fun MessagingContext.setRequest(request: GetChannelRequest) {
    request.filter?.let { filter ->
        page = Page(filter.pageSize ?: 10, filter.pageNumber ?: 0)
        filter.channelIds?.let { list -> channelIds = list.map { ChannelId(it) } }
        filter.pageNumber?.let { page = page.copy(pageNumber = it) }
        filter.pageSize?.let { page = page.copy(pageSize = it) }
    }
}

private fun MessagingContext.handleMessage(channelMessageDto: ChannelMessageDto) {
    return channelMessageDto.run {
        when {
            profileIdTo != null -> this@handleMessage.instantMessage = InstantMessage(
                id = MessageId(UUID.randomUUID().toString()),
                profileIdFrom = profileIdFrom?.let { ProfileId(it) } ?: ProfileId.NONE,
                profileIdTo = profileIdTo?.let { ProfileId(it) } ?: ProfileId.NONE,
                messageText = messageText ?: "",
                resourceLinks = resourceLinks
            )
            channelId != null -> this@handleMessage.channelMessage = ChannelMessage(
                id = MessageId(UUID.randomUUID().toString()),
                profileIdFrom = profileIdFrom?.let { ProfileId(it) } ?: ProfileId.NONE,
                channelId = channelId?.let { ChannelId(it) } ?: ChannelId.NONE,
                messageText = messageText ?: "",
                resourceLinks = resourceLinks
            )
            else -> throw IllegalArgumentException("Message without an addressee")
        }
    }
}

inline fun setStub(stubCase: StubCase?, successStubCase: ContextStubCase): ContextStubCase =
    when (stubCase) {
        StubCase.SUCCESS -> successStubCase
        else -> NONE
    }

inline fun setStub(stubCase: DebugStub?, successStubCase: ContextStubCase): ContextStubCase =
    when (stubCase) {
        DebugStub.SUCCESS -> successStubCase
        else -> NONE
    }
