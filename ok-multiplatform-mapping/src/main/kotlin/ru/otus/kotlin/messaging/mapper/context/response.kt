package ru.otus.kotlin.messaging.mapper.context

import ru.otus.kotlin.messaging.Error
import ru.otus.kotlin.messaging.api.model.common.dto.CommonResponseStatus
import ru.otus.kotlin.messaging.api.model.common.error.CommonErrorDto
import ru.otus.kotlin.messaging.api.model.common.error.CommonErrorSeverity
import ru.otus.kotlin.messaging.api.model.message.CreateChannelMessageResponse
import java.time.LocalDateTime
import java.util.*

fun CreateChannelMessageResponse.toDto(
    transportContext: TransportContext,
    responseStatus: CommonResponseStatus = CommonResponseStatus.SUCCESS
): CreateChannelMessageResponse =
    CreateChannelMessageResponse(
        responseId = UUID.randomUUID().toString(),
        responseTime = LocalDateTime.now().toString(),
        errors = transportContext.messagingContext.errors.toDto(),
        status = CommonResponseStatus.valueOf(responseStatus.name.toUpperCase()),
        request = transportContext.commonContext.request
    ).also { transportContext.commonContext.response = it }

fun List<Error>.toDto(): List<CommonErrorDto> {
    return map { error ->
        CommonErrorDto(
            code = error.code,
            level = CommonErrorSeverity.valueOf(error.level.name.toUpperCase()),
            message = error.message
        )
    }.toList()
}
