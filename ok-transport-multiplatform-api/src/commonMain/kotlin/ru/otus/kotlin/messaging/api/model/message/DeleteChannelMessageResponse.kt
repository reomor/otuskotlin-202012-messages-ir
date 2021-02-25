package ru.otus.kotlin.messaging.api.model.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.kotlin.messaging.api.model.common.AbstractResponse
import ru.otus.kotlin.messaging.api.model.common.Request
import ru.otus.kotlin.messaging.api.model.common.dto.DebugMode
import ru.otus.kotlin.messaging.api.model.common.dto.ResponseStatus
import ru.otus.kotlin.messaging.api.model.common.error.ErrorDto

@Serializable
@SerialName("DeleteChannelMessageResponse")
data class DeleteChannelMessageResponse(
    val debug: DebugDto? = null,
    override val responseId: String?,
    override val responseTime: String?,
    override val errors: List<ErrorDto>?,
    override val status: ResponseStatus?,
    override val request: Request
) : AbstractResponse() {

    @Serializable
    data class DebugDto(
        val mode: DebugMode
    )
}
