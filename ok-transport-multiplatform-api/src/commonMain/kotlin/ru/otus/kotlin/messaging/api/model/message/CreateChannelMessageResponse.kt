package ru.otus.kotlin.messaging.api.model.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.kotlin.messaging.api.model.common.AbstractResponse
import ru.otus.kotlin.messaging.api.model.common.Request
import ru.otus.kotlin.messaging.api.model.common.dto.DebugMode
import ru.otus.kotlin.messaging.api.model.common.dto.CommonResponseStatus
import ru.otus.kotlin.messaging.api.model.common.dto.CommonResponseStatus.SUCCESS
import ru.otus.kotlin.messaging.api.model.common.error.CommonErrorDto

@Serializable
@SerialName("CreateChannelMessageResponse")
data class  CreateChannelMessageResponse(
    val debug: DebugDto? = null,
    override val responseId: String? = null,
    override val responseTime: String? = null,
    override val errors: List<CommonErrorDto>? = emptyList(),
    override val status: CommonResponseStatus? = SUCCESS,
    override val request: Request? = null
) : AbstractResponse() {

    @Serializable
    data class DebugDto(
        val mode: DebugMode
    )
}
