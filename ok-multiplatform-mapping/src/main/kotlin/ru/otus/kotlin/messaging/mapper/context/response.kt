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
import java.time.LocalDateTime
import java.util.*

fun CreateChannelMessageResponse.fromContext(
    transportContext: TransportContext,
    responseStatus: CommonResponseStatus = CommonResponseStatus.SUCCESS
): CreateChannelMessageResponse =
    transportContext.addToCommonContext {
        CreateChannelMessageResponse(
            responseId = transportContext.responseId,
            responseTime = transportContext.responseTime,
            errors = transportContext.messagingContext.errors.toCommonErrorDto(),
            status = CommonResponseStatus.valueOf(responseStatus.name.toUpperCase()),
            request = transportContext.commonContext.request
        )
    }

fun DeleteChannelMessageResponse.fromContext(
    transportContext: TransportContext,
    responseStatus: CommonResponseStatus = CommonResponseStatus.SUCCESS
): DeleteChannelMessageResponse =
    transportContext.addToCommonContext {
        DeleteChannelMessageResponse(
            responseId = transportContext.responseId,
            responseTime = transportContext.responseTime,
            errors = transportContext.messagingContext.errors.toCommonErrorDto(),
            status = CommonResponseStatus.valueOf(responseStatus.name.toUpperCase()),
            request = transportContext.commonContext.request
        )
    }

fun EditChannelMessageResponse.fromContext(
    transportContext: TransportContext,
    responseStatus: CommonResponseStatus = CommonResponseStatus.SUCCESS
): EditChannelMessageResponse =
    transportContext.addToCommonContext {
        EditChannelMessageResponse(
            responseId = transportContext.responseId,
            responseTime = transportContext.responseTime,
            errors = transportContext.messagingContext.errors.toCommonErrorDto(),
            status = CommonResponseStatus.valueOf(responseStatus.name.toUpperCase()),
            request = transportContext.commonContext.request
        )
    }

fun GetChannelMessageResponse.fromContext(
    transportContext: TransportContext,
    responseStatus: CommonResponseStatus = CommonResponseStatus.SUCCESS
): GetChannelMessageResponse =
    transportContext.addToCommonContext {
        GetChannelMessageResponse(
            responseId = transportContext.responseId,
            responseTime = transportContext.responseTime,
            data = transportContext.messagingContext.messages.map { it.toMessageDto() },
            errors = transportContext.messagingContext.errors.toCommonErrorDto(),
            status = CommonResponseStatus.valueOf(responseStatus.name.toUpperCase()),
            request = transportContext.commonContext.request
        )
    }

fun CreateChannelResponse.fromContext(
    transportContext: TransportContext,
    responseStatus: CommonResponseStatus = CommonResponseStatus.SUCCESS
): CreateChannelResponse =
    transportContext.addToOpenApiContext {
        CreateChannelResponse(
            type = "CreateChannelResponse",
            responseId = transportContext.responseId,
            responseTime = transportContext.responseTime,
            errors = transportContext.messagingContext.errors.toOpenApiErrorDto(),
            status = ResponseStatus.valueOf(responseStatus.name.toUpperCase()),
            request = transportContext.openApiContext.request,
            channel = transportContext.messagingContext.channel.toDto()
        )
    }

fun DeleteChannelResponse.fromContext(
    transportContext: TransportContext,
    responseStatus: CommonResponseStatus = CommonResponseStatus.SUCCESS
): DeleteChannelResponse =
    transportContext.addToOpenApiContext {
        DeleteChannelResponse(
            responseId = transportContext.responseId,
            responseTime = transportContext.responseTime,
            errors = transportContext.messagingContext.errors.toOpenApiErrorDto(),
            status = ResponseStatus.valueOf(responseStatus.name.toUpperCase()),
            request = transportContext.openApiContext.request,
            channel = transportContext.messagingContext.channel.toDto()
        )
    }

fun GetChannelResponse.fromContext(
    transportContext: TransportContext,
    responseStatus: CommonResponseStatus = CommonResponseStatus.SUCCESS
): GetChannelResponse =
    transportContext.addToOpenApiContext {
        GetChannelResponse(
            responseId = transportContext.responseId,
            responseTime = transportContext.responseTime,
            errors = transportContext.messagingContext.errors.toOpenApiErrorDto(),
            status = ResponseStatus.valueOf(responseStatus.name.toUpperCase()),
            request = transportContext.openApiContext.request,
            channels = transportContext.messagingContext.channels.map { it.toDto() }
        )
    }

private fun <T : Response> TransportContext.addToCommonContext(block: () -> T): T =
    block().also { commonContext.response = it }

private fun <T : Response> TransportContext.addToCommonContext(baseResponse: T, block: (response: T) -> T): T =
    block(baseResponse).also { commonContext.response = it }

private fun <T : BaseMessage> TransportContext.addToOpenApiContext(block: () -> T): T =
    block().also { openApiContext.response = it }

private fun <T : BaseMessage> TransportContext.addToOpenApiContext(baseResponse: T, block: (response: T) -> T): T =
    block(baseResponse).also { openApiContext.response = it }

fun Message.toMessageDto(): ChannelMessageDto {
    this.run {
        return when (this) {
            is InstantMessage -> ChannelMessageDto(
                profileIdFrom = profileIdFrom.id,
                profileIdTo = profileIdTo.id,
                messageText = messageText,
                resourceLinks = resourceLinks
            )
            is ChannelMessage -> ChannelMessageDto(
                profileIdFrom = profileIdFrom.id,
                channelId = channelId.id,
                messageText = messageText,
                resourceLinks = resourceLinks
            )
        }
    }
}

fun List<Error>.toCommonErrorDto(): List<CommonErrorDto> {
    return map { error ->
        CommonErrorDto(
            code = error.code,
            level = CommonErrorSeverity.valueOf(error.level.name.toUpperCase()),
            message = error.message
        )
    }.toList()
}

fun List<Error>.toOpenApiErrorDto(): List<ErrorDto> {
    return map { error ->
        ErrorDto(
            code = error.code,
            level = ErrorSeverity.valueOf(error.level.name.toUpperCase()),
            message = error.message
        )
    }.toList()
}

fun Channel.toDto(): ChannelDto =
    ChannelDto(
        id = this.channelId.id,
        name = this.name,
        ownerId = this.ownerId.id,
        type = ChannelType.valueOf(this.type.name.toUpperCase())
    )
