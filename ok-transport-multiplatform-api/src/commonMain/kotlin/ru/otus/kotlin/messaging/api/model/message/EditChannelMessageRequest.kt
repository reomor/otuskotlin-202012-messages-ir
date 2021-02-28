package ru.otus.kotlin.messaging.api.model.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.kotlin.messaging.api.model.common.AbstractRequest
import ru.otus.kotlin.messaging.api.model.common.dto.DebugMode
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageDto

@Serializable
@SerialName("EditChannelMessageRequest")
data class EditChannelMessageRequest(
    val debug: DebugDto? = null,
    val messageId: String? = null,
    val channelId: String? = null,
    val data: ChannelMessageDto? = null,
    override val requestId: String? = null,
    override val requestTime: String? = null
) : AbstractRequest() {

    @Serializable
    data class DebugDto(
        val mode: DebugMode
    )
}
