package ru.otus.kotlin.messaging.mapper.context

import ru.otus.kotlin.messaging.*
import ru.otus.kotlin.messaging.context.MessagingContext
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.DeleteChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.EditChannelMessageRequest
import ru.otus.kotlin.messaging.api.model.message.GetChannelMessageRequest
import ru.otus.kotlin.messaging.api.validation.validator.ChannelMessageFilterValidator
import ru.otus.kotlin.messaging.api.validation.validator.ChannelMessageValidator
import ru.otus.kotlin.messaging.context.MessagingContext.Companion.log
import ru.otus.kotlin.messaging.openapi.channel.models.CreateChannelRequest
import ru.otus.kotlin.messaging.openapi.channel.models.DeleteChannelRequest
import ru.otus.kotlin.messaging.openapi.channel.models.GetChannelRequest
import java.util.*

fun MessagingContext.setRequest(request: CreateChannelMessageRequest) {
    request.data?.let { channelMessageDto ->
        val validationResult = ChannelMessageValidator.validate(channelMessageDto)
        if (validationResult.isSuccess) {
            channelMessageDto.apply {
                when {
                    profileIdTo != null -> this@setRequest.instantMessage = InstantMessage(
                        id = MessageId(UUID.randomUUID().toString()),
                        profileIdFrom = profileIdFrom?.let { ProfileId(it) } ?: ProfileId.NONE,
                        profileIdTo = profileIdTo?.let { ProfileId(it) } ?: ProfileId.NONE,
                        messageText = messageText ?: "",
                        resourceLinks = resourceLinks
                    )
                    channelId != null -> this@setRequest.channelMessage = ChannelMessage(
                        id = MessageId(UUID.randomUUID().toString()),
                        profileIdFrom = profileIdFrom?.let { ProfileId(it) } ?: ProfileId.NONE,
                        channelId = channelId?.let { ChannelId(it) } ?: ChannelId.NONE,
                        messageText = messageText ?: "",
                        resourceLinks = resourceLinks
                    )
                }
            }
        } else {
            log.error("Errors during validation: {}", validationResult.errors)
        }
    }
}

fun MessagingContext.setRequest(request: DeleteChannelMessageRequest) {
    request.messageId?.let { messageId -> this.messageIds = listOf(MessageId(messageId)) }
    request.channelId?.let { channelId -> this.channelId = ChannelId(channelId) }
}

fun MessagingContext.setRequest(request: EditChannelMessageRequest) {
    request.messageId?.let { messageId ->
        messageIds = listOf(MessageId(messageId))
        request.channelId?.let { channelId = ChannelId(it) }
        request.data?.let { channelMessageDto ->
            val validationResult = ChannelMessageValidator.validate(channelMessageDto)
            if (validationResult.isSuccess) {
                channelMessageDto.apply {
                    when {
                        profileIdTo != null -> this@setRequest.instantMessage = InstantMessage(
                            id = MessageId(UUID.randomUUID().toString()),
                            profileIdFrom = profileIdFrom?.let { ProfileId(it) } ?: ProfileId.NONE,
                            profileIdTo = profileIdTo?.let { ProfileId(it) } ?: ProfileId.NONE,
                            messageText = messageText ?: "",
                            resourceLinks = resourceLinks
                        )
                        channelId != null -> this@setRequest.channelMessage = ChannelMessage(
                            id = MessageId(UUID.randomUUID().toString()),
                            profileIdFrom = profileIdFrom?.let { ProfileId(it) } ?: ProfileId.NONE,
                            channelId = channelId?.let { ChannelId(it) } ?: ChannelId.NONE,
                            messageText = messageText ?: "",
                            resourceLinks = resourceLinks
                        )
                    }
                }
            } else {
                log.error("Errors during validation: {}", validationResult.errors)
            }
        }
    }
}

fun MessagingContext.setRequest(request: GetChannelMessageRequest) {
    request.filter?.let { filter ->
        val validationResult = ChannelMessageFilterValidator.validate(filter)
        if (validationResult.isSuccess) {
            filter.channelId?.let { channelId = ChannelId(it) }
            filter.profileIdFrom?.let { profileIdFrom = ProfileId(it) }
            filter.profileIdTo?.let { profileIdTo = ProfileId(it) }
            messageIds = filter.messageIds.map { messageId -> MessageId(messageId) }
            page = Page(filter.pageSize, filter.pageNumber)
        } else {
            log.error("Errors during validation: {}", validationResult.errors)
        }
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
    request.filter?.let {
        filter ->
        page = Page(filter.pageSize ?: 10, filter.pageNumber ?: 0)
        filter.channelIds?.let { list -> channelIds = list.map { ChannelId(it) } }
        filter.pageNumber?.let { page = page.copy(pageNumber = it) }
        filter.pageSize?.let { page = page.copy(pageSize = it) }
    }
}
