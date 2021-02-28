package ru.otus.kotlin.messaging.mapper.context

import ru.otus.kotlin.messaging.*
import ru.otus.kotlin.messaging.api.model.common.Response
import ru.otus.kotlin.messaging.api.model.common.dto.CommonResponseStatus
import ru.otus.kotlin.messaging.api.model.common.error.CommonErrorDto
import ru.otus.kotlin.messaging.api.model.common.error.CommonErrorSeverity
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageResponse
import ru.otus.kotlin.messaging.api.model.message.DeleteChannelMessageResponse
import ru.otus.kotlin.messaging.api.model.message.EditChannelMessageResponse
import ru.otus.kotlin.messaging.api.model.message.GetChannelMessageResponse
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageDto
import ru.otus.kotlin.messaging.openapi.channel.models.*
import ru.otus.kotlin.messaging.openapi.channel.models.ChannelType
import ru.otus.kotlin.messaging.openapi.channel.models.ErrorSeverity

fun CreateChannelMessageResponse.toDto(
    transportContext: TransportContext,
    responseStatus: CommonResponseStatus = CommonResponseStatus.SUCCESS
): CreateChannelMessageResponse =
    transportContext.addToCommonContext(this) { response ->
        CreateChannelMessageResponse(
            responseId = response.responseId,
            responseTime = response.responseTime,
            errors = transportContext.messagingContext.errors.toCommonErrorDto(),
            status = CommonResponseStatus.valueOf(responseStatus.name.toUpperCase()),
            request = transportContext.commonContext.request
        )
    }

fun DeleteChannelMessageResponse.toDto(
    transportContext: TransportContext,
    responseStatus: CommonResponseStatus = CommonResponseStatus.SUCCESS
): DeleteChannelMessageResponse =
    transportContext.addToCommonContext(this) { response ->
        DeleteChannelMessageResponse(
            responseId = response.responseId,
            responseTime = response.responseTime,
            errors = transportContext.messagingContext.errors.toCommonErrorDto(),
            status = CommonResponseStatus.valueOf(responseStatus.name.toUpperCase()),
            request = transportContext.commonContext.request
        )
    }

fun EditChannelMessageResponse.toDto(
    transportContext: TransportContext,
    responseStatus: CommonResponseStatus = CommonResponseStatus.SUCCESS
): EditChannelMessageResponse =
    transportContext.addToCommonContext(this) { response ->
        EditChannelMessageResponse(
            responseId = response.responseId,
            responseTime = response.responseTime,
            errors = transportContext.messagingContext.errors.toCommonErrorDto(),
            status = CommonResponseStatus.valueOf(responseStatus.name.toUpperCase()),
            request = transportContext.commonContext.request
        )
    }

fun GetChannelMessageResponse.toDto(
    transportContext: TransportContext,
    responseStatus: CommonResponseStatus = CommonResponseStatus.SUCCESS
): GetChannelMessageResponse =
    transportContext.addToCommonContext(this) { response ->
        GetChannelMessageResponse(
            responseId = response.responseId,
            responseTime = response.responseTime,
            data = transportContext.messagingContext.messages.toMessageDto(),
            errors = transportContext.messagingContext.errors.toCommonErrorDto(),
            status = CommonResponseStatus.valueOf(responseStatus.name.toUpperCase()),
            request = transportContext.commonContext.request
        )
    }

fun CreateChannelResponse.toDto(
    transportContext: TransportContext,
    responseStatus: CommonResponseStatus = CommonResponseStatus.SUCCESS
): CreateChannelResponse =
    transportContext.addToOpenApiContext(this) { response ->
        CreateChannelResponse(
            responseId = response.responseId,
            responseTime = response.responseTime,
            errors = transportContext.messagingContext.errors.toOpenApiErrorDto(),
            status = ResponseStatus.valueOf(responseStatus.name.toUpperCase()),
            request = transportContext.openApiContext.request,
            channel = transportContext.messagingContext.channel.toDto()
        )
    }

fun DeleteChannelResponse.toDto(
    transportContext: TransportContext,
    responseStatus: CommonResponseStatus = CommonResponseStatus.SUCCESS
): DeleteChannelResponse =
    transportContext.addToOpenApiContext(this) { response ->
        DeleteChannelResponse(
            responseId = response.responseId,
            responseTime = response.responseTime,
            errors = transportContext.messagingContext.errors.toOpenApiErrorDto(),
            status = ResponseStatus.valueOf(responseStatus.name.toUpperCase()),
            request = transportContext.openApiContext.request,
            channel = transportContext.messagingContext.channel.toDto()
        )
    }

fun GetChannelResponse.toDto(
    transportContext: TransportContext,
    responseStatus: CommonResponseStatus = CommonResponseStatus.SUCCESS
): GetChannelResponse =
    transportContext.addToOpenApiContext(this) { response ->
        GetChannelResponse(
            responseId = response.responseId,
            responseTime = response.responseTime,
            errors = transportContext.messagingContext.errors.toOpenApiErrorDto(),
            status = ResponseStatus.valueOf(responseStatus.name.toUpperCase()),
            request = transportContext.openApiContext.request,
            channels = transportContext.messagingContext.channels.map { it.toDto() }
        )
    }

private fun <T : Response> TransportContext.addToCommonContext(baseResponse: T, block: (response: T) -> T): T =
    block.invoke(baseResponse).also { commonContext.response = it }

private fun <T : BaseMessage> TransportContext.addToOpenApiContext(baseResponse: T, block: (response: T) -> T): T =
    block.invoke(baseResponse).also { openApiContext.response = it }

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
                channelId = message.channelId.id,
                messageText = message.messageText,
                resourceLinks = message.resourceLinks
            )
        }
    }

private fun List<Error>.toCommonErrorDto(): List<CommonErrorDto> {
    return map { error ->
        CommonErrorDto(
            code = error.code,
            level = CommonErrorSeverity.valueOf(error.level.name.toUpperCase()),
            message = error.message
        )
    }.toList()
}

private fun List<Error>.toOpenApiErrorDto(): List<ErrorDto> {
    return map { error ->
        ErrorDto(
            code = error.code,
            level = ErrorSeverity.valueOf(error.level.name.toUpperCase()),
            message = error.message
        )
    }.toList()
}

private fun Channel.toDto(): ChannelDto =
    ChannelDto(
        id = this.channelId.id,
        name = this.name,
        ownerId = this.ownerId.id,
        type = ChannelType.valueOf(this.type.name.toUpperCase())
    )
