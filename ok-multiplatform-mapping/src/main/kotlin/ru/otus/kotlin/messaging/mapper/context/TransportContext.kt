package ru.otus.kotlin.messaging.mapper.context

import ru.otus.kotlin.messaging.api.model.common.Request
import ru.otus.kotlin.messaging.api.model.common.Response
import ru.otus.kotlin.messaging.context.MessagingContext
import ru.otus.kotlin.messaging.openapi.channel.models.BaseRequest
import ru.otus.kotlin.messaging.openapi.channel.models.BaseResponse

data class TransportContext(
    var messagingContext: MessagingContext = MessagingContext(),
    var commonContext: CommonContext = CommonContext(),
    var openApiContext: OpenApiContext = OpenApiContext()
)

data class CommonContext(
    var request: Request? = null,
    var response: Response? = null
)

data class OpenApiContext(
    var request: BaseRequest? = null,
    var response: BaseResponse? = null
)
