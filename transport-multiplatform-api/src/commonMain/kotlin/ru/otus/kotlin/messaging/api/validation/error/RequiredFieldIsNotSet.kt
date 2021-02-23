package ru.otus.kotlin.messaging.api.validation.error

import ru.otus.kotlin.messaging.api.validation.ValidationError

class RequiredFieldIsNotSet(
    field: String
) : ValidationError {
    override val message = "Required field $field is not set or null"
}
