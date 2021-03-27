package ru.otus.kotlin.messaging.api.model.common.dto

import kotlinx.serialization.Serializable

@Serializable
enum class StubCase {
    NONE,
    SUCCESS,
    FAIL
}
