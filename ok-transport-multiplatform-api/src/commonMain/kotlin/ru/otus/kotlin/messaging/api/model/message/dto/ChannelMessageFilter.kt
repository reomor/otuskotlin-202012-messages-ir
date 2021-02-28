package ru.otus.kotlin.messaging.api.model.message.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChannelMessageFilter(
    val messageIds: List<String> = mutableListOf(),
    val profileIdFrom: String? = null,
    val profileIdTo: String? = null,
    val channelId: String? = null,
    val pageSize: Int = 10,
    val pageNumber: Int = 0
)
