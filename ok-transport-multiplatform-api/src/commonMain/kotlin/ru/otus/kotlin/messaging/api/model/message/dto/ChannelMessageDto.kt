package ru.otus.kotlin.messaging.api.model.message.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChannelMessageDto(
    val profileIdFrom: String? = null,
    val profileIdTo: String? = null,
    val channelId: String? = null,
    val messageText: String? = "",
    val resourceLinks: List<String> = mutableListOf()
)
