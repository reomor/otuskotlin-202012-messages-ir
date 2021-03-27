package ru.otus.kotlin.messaging.api.model.common.dto

import ru.otus.kotlin.messaging.api.model.common.dto.StubCase

interface IDebug {
    val mode: DebugMode?
    val stubCase: StubCase?
}
