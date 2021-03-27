package ru.otus.kotlin.messaging.api.model.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.kotlin.messaging.api.model.common.AbstractRequest
import ru.otus.kotlin.messaging.api.model.common.dto.DebugMode
import ru.otus.kotlin.messaging.api.model.common.dto.IDebug
import ru.otus.kotlin.messaging.api.model.common.dto.StubCase
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageFilter

@Serializable
@SerialName("GetChannelMessageRequest")
data class GetChannelMessageRequest(
    val debug: DebugDto? = null,
    val filter: ChannelMessageFilter? = null,
    override val requestId: String? = null,
    override val requestTime: String? = null
) : AbstractRequest() {

    @Serializable
    data class DebugDto(
        override val mode: DebugMode? = null,
        override val stubCase: StubCase? = null
    ) : IDebug
}
