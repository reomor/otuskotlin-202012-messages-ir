package ru.otus.kotlin.messaging.api.model.common.dto

import kotlinx.serialization.Serializable

@Serializable
data class DebugDto(
 val mode: DebugMode
)
