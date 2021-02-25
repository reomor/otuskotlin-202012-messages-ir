package ru.otus.kotlin.messaging.api.model.common.error

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto(
 val code: String? = null,
 val level: ErrorSeverity? = null,
 val message: String? = null
)
