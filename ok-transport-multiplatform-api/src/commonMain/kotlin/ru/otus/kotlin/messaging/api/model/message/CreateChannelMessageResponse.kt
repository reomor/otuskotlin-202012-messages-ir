package ru.otus.kotlin.messaging.api.model.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.kotlin.messaging.api.model.common.AbstractResponse
import ru.otus.kotlin.messaging.api.model.common.Request
import ru.otus.kotlin.messaging.api.model.common.dto.DebugMode
import ru.otus.kotlin.messaging.api.model.common.dto.ResponseStatus
import ru.otus.kotlin.messaging.api.model.common.dto.ResponseStatus.SUCCESS
import ru.otus.kotlin.messaging.api.model.common.error.ErrorDto

@Serializable
@SerialName("CreateChannelMessageResponse")
data class  CreateChannelMessageResponse(
    val debug: DebugDto? = null,
    override val responseId: String? = null,
    override val responseTime: String? = null,
    override val errors: List<ErrorDto>? = emptyList(),
    override val status: ResponseStatus? = SUCCESS,
    override val request: Request
) : AbstractResponse() {

    @Serializable
    data class DebugDto(
        val mode: DebugMode
    )
}
