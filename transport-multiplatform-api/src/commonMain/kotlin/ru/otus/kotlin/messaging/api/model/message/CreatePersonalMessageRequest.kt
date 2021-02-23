package ru.otus.kotlin.messaging.api.model.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.kotlin.messaging.api.model.common.AbstractRequest
import ru.otus.kotlin.messaging.api.model.common.dto.DebugDto
import ru.otus.kotlin.messaging.api.model.message.dto.PersonalMessageDto

@Serializable
@SerialName("CreatePersonalMessageRequest")
data class CreatePersonalMessageRequest(
    override val requestId: String?,
    override val requestTime: String?,
    override val debug: DebugDto?,
    val data: PersonalMessageDto? = null
) : AbstractRequest()
