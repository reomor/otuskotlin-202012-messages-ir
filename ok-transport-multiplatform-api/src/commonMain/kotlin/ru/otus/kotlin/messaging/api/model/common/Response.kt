package ru.otus.kotlin.messaging.api.model.common

import ru.otus.kotlin.messaging.api.model.common.dto.CommonResponseStatus
import ru.otus.kotlin.messaging.api.model.common.error.CommonErrorDto

interface Response {
    val responseId: String?
    val responseTime: String?
    val errors: List<CommonErrorDto>?
    val status: CommonResponseStatus?
    val request: Request?
}
