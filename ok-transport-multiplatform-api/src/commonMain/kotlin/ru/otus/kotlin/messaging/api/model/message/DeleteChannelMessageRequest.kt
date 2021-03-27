package ru.otus.kotlin.messaging.api.model.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.kotlin.messaging.api.model.common.AbstractRequest
import ru.otus.kotlin.messaging.api.model.common.dto.DebugMode
import ru.otus.kotlin.messaging.api.model.common.dto.IDebug
import ru.otus.kotlin.messaging.api.model.common.dto.StubCase

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
        override val mode: DebugMode? = null,
        override val stubCase: StubCase? = null
    ) : IDebug
}
