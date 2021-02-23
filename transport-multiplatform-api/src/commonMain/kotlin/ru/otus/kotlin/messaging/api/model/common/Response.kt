package ru.otus.kotlin.messaging.api.model.common

import ru.otus.kotlin.messaging.api.model.common.dto.DebugDto
import ru.otus.kotlin.messaging.api.model.common.dto.ResponseStatus
import ru.otus.kotlin.messaging.api.model.common.error.ErrorDto

interface Response {
    val responseId: String?
    val responseTime: String?
    val errors: List<ErrorDto>?
    val status: ResponseStatus?
    val debug: DebugDto?
    val request: Request
}
