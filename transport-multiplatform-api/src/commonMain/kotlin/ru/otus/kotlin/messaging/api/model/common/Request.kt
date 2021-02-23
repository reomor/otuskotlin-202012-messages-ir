package ru.otus.kotlin.messaging.api.model.common

import ru.otus.kotlin.messaging.api.model.common.dto.DebugDto

interface Request {
    val requestId: String?
    val requestTime: String?
    val debug: DebugDto?
}
