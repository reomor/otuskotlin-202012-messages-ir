package ru.otus.kotlin.messaging.api.model.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.kotlin.messaging.api.model.common.AbstractRequest
import ru.otus.kotlin.messaging.api.model.common.dto.DebugMode

@Serializable
@SerialName("DeleteChannelMessageRequest")
data class DeleteChannelMessageRequest(
    val debug: DebugDto? = null,
    val messageId: String? = null,
    val channelId: String? = null,
    override val requestId: String? = null,
    override val requestTime: String? = null
) : AbstractRequest() {

    @Serializable
    data class DebugDto(
        val mode: DebugMode
    )
}
