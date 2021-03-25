package ru.otus.kotlin.messaging.mapper.context

import ru.otus.kotlin.messaging.api.model.common.Request
import ru.otus.kotlin.messaging.api.model.common.Response
import ru.otus.kotlin.messaging.context.MessagingContext
import ru.otus.kotlin.messaging.openapi.channel.models.BaseMessage
import java.time.LocalDateTime
import java.util.*

data class TransportContext(
    var status: TransportContextStatus = TransportContextStatus.NONE,
    var stubCase: ContextStubCase = ContextStubCase.NONE,

    var messagingContext: MessagingContext = MessagingContext(),
    var commonContext: CommonContext = CommonContext(),
    var openApiContext: OpenApiContext = OpenApiContext(),

    private var _responseId: String = UUID.randomUUID().toString(),
    private var _responseTime: String? = null
) {
    var responseId: String
        get() = _responseId
        set(value) {
            _responseId = value
        }

    var responseTime: String
        get() = _responseTime ?: LocalDateTime.now().toString()
        set(value) {
            _responseTime = value
        }
}

data class CommonContext(
    var request: Request? = null,
    var response: Response? = null
)

data class OpenApiContext(
    var request: BaseMessage? = null,
    var response: BaseMessage? = null
)
