package ru.otus.kotlin.messaging.api.model.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.kotlin.messaging.api.model.common.AbstractRequest
import ru.otus.kotlin.messaging.api.model.common.dto.DebugMode
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageDto

@Serializable
@SerialName("CreateChannelMessageRequest")
data class CreateChannelMessageRequest(
    val debug: DebugDto? = null,
    val data: ChannelMessageDto? = null,
    override val requestId: String?,
    override val requestTime: String?
) : AbstractRequest() {

    @Serializable
    data class DebugDto(
        val mode: DebugMode
    )
}
