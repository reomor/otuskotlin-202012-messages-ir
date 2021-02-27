package ru.otus.kotlin.messaging.api.model.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.kotlin.messaging.api.model.common.AbstractResponse
import ru.otus.kotlin.messaging.api.model.common.Request
import ru.otus.kotlin.messaging.api.model.common.dto.DebugMode
import ru.otus.kotlin.messaging.api.model.common.dto.CommonResponseStatus
import ru.otus.kotlin.messaging.api.model.common.error.CommonErrorDto

@Serializable
@SerialName("EditChannelMessageResponse")
data class EditChannelMessageResponse(
    val debug: DebugDto? = null,
    override val responseId: String?,
    override val responseTime: String?,
    override val errors: List<CommonErrorDto>?,
    override val status: CommonResponseStatus?,
    override val request: Request? = null
) : AbstractResponse() {

    @Serializable
    data class DebugDto(
        val mode: DebugMode
    )
}
