package ru.otus.kotlin.messaging.api.model.common.error

import kotlinx.serialization.Serializable

@Serializable
data class CommonErrorDto(
 val code: String? = null,
 val level: CommonErrorSeverity? = null,
 val message: String? = null
)
