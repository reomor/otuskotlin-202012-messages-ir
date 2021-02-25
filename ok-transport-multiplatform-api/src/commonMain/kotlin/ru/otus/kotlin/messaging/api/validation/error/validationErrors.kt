package ru.otus.kotlin.messaging.api.validation.error

import ru.otus.kotlin.messaging.api.validation.ValidationError

class ObjectIsNull(
    objectName: String = ""
) : ValidationError {
    override val message = "Object \"$objectName\" is null"
}

class RequiredFieldIsNotSet(
    field: String
) : ValidationError {
    override val message = "Required field \"$field\" is not set or null"
}

class OnlyOneFieldMustBeSet(
    field: String,
    fieldAlternative: String
) : ValidationError {
    override val message = "The only one of fields \"$field\" and \"$fieldAlternative\" must be set"
}

class FieldBreaksConstraint(
    field: String,
    constraint: String = ""
) : ValidationError {
    override val message = "Field \"$field\" breaks constraint \"$constraint\""
}
