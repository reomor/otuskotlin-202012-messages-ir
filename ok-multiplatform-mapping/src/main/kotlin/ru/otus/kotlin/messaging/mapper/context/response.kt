package ru.otus.kotlin.messaging.mapper.context

import ru.otus.kotlin.messaging.ChannelMessage
import ru.otus.kotlin.messaging.Error
import ru.otus.kotlin.messaging.InstantMessage
import ru.otus.kotlin.messaging.Message
import ru.otus.kotlin.messaging.api.model.common.Response
import ru.otus.kotlin.messaging.api.model.common.dto.CommonResponseStatus
import ru.otus.kotlin.messaging.api.model.common.error.CommonErrorDto
import ru.otus.kotlin.messaging.api.model.common.error.CommonErrorSeverity
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageResponse
import ru.otus.kotlin.messaging.api.model.message.DeleteChannelMessageResponse
import ru.otus.kotlin.messaging.api.model.message.EditChannelMessageResponse
import ru.otus.kotlin.messaging.api.model.message.GetChannelMessageResponse
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageDto

fun CreateChannelMessageResponse.toDto(
    transportContext: TransportContext,
    responseStatus: CommonResponseStatus = CommonResponseStatus.SUCCESS
): CreateChannelMessageResponse =
    transportContext.addToContext(this) { response ->
        CreateChannelMessageResponse(
            responseId = response.responseId,
            responseTime = response.responseTime,
            errors = transportContext.messagingContext.errors.toErrorDto(),
            status = CommonResponseStatus.valueOf(responseStatus.name.toUpperCase()),
            request = transportContext.commonContext.request
        )
    }

fun DeleteChannelMessageResponse.toDto(
    transportContext: TransportContext,
    responseStatus: CommonResponseStatus = CommonResponseStatus.SUCCESS
): DeleteChannelMessageResponse =
    transportContext.addToContext(this) { response ->
        DeleteChannelMessageResponse(
            responseId = response.responseId,
            responseTime = response.responseTime,
            errors = transportContext.messagingContext.errors.toErrorDto(),
            status = CommonResponseStatus.valueOf(responseStatus.name.toUpperCase()),
            request = transportContext.commonContext.request
        )
    }

fun EditChannelMessageResponse.toDto(
    transportContext: TransportContext,
    responseStatus: CommonResponseStatus = CommonResponseStatus.SUCCESS
): EditChannelMessageResponse =
    transportContext.addToContext(this) { response ->
        EditChannelMessageResponse(
            responseId = response.responseId,
            responseTime = response.responseTime,
            errors = transportContext.messagingContext.errors.toErrorDto(),
            status = CommonResponseStatus.valueOf(responseStatus.name.toUpperCase()),
            request = transportContext.commonContext.request
        )
    }

fun GetChannelMessageResponse.toDto(
    transportContext: TransportContext,
    responseStatus: CommonResponseStatus = CommonResponseStatus.SUCCESS
): GetChannelMessageResponse =
    transportContext.addToContext(this) { response ->
        GetChannelMessageResponse(
            responseId = response.responseId,
            responseTime = response.responseTime,
            data = transportContext.messagingContext.messages.toMessageDto(),
            errors = transportContext.messagingContext.errors.toErrorDto(),
            status = CommonResponseStatus.valueOf(responseStatus.name.toUpperCase()),
            request = transportContext.commonContext.request
        )
    }

private fun <T : Response> TransportContext.addToContext(baseResponse: T, block: (response: T) -> T): T =
    block.invoke(baseResponse).also { commonContext.response = it }

private fun List<Message>.toMessageDto(): List<ChannelMessageDto> =
    map { message ->
        when (message) {
            is InstantMessage -> ChannelMessageDto(
                profileIdFrom = message.profileIdFrom.id,
                profileIdTo = message.profileIdTo.id,
                messageText = message.messageText,
                resourceLinks = message.resourceLinks
            )
            is ChannelMessage -> ChannelMessageDto(
                profileIdFrom = message.profileIdFrom.id,
                channelIdTo = message.channelId.id,
                messageText = message.messageText,
                resourceLinks = message.resourceLinks
            )
        }
    }

private fun List<Error>.toErrorDto(): List<CommonErrorDto> {
    return map { error ->
        CommonErrorDto(
            code = error.code,
            level = CommonErrorSeverity.valueOf(error.level.name.toUpperCase()),
            message = error.message
        )
    }.toList()
}
