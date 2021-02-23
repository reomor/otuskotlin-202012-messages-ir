package ru.otus.kotlin.messaging.api.model.message.dto

import kotlinx.serialization.Serializable

@Serializable
data class PersonalMessageDto(
    val profileIdFrom: String? = null,
    val profileIdTo: String? = null,
    val message: String? = "",
    val resourceLinks: List<String> = emptyList()
)
