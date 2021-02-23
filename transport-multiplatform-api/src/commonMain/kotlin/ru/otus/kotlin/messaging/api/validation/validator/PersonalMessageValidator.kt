package ru.otus.kotlin.messaging.api.validation.validator

import ru.otus.kotlin.messaging.api.model.message.dto.PersonalMessageDto
import ru.otus.kotlin.messaging.api.validation.ValidationError
import ru.otus.kotlin.messaging.api.validation.ValidationResult
import ru.otus.kotlin.messaging.api.validation.Validator
import ru.otus.kotlin.messaging.api.validation.error.RequiredFieldIsNotSet

object PersonalMessageValidator : Validator<PersonalMessageDto?> {
    override fun validate(obj: PersonalMessageDto?): ValidationResult {
        val validationErrors = mutableListOf<ValidationError>()
        if (obj?.profileIdFrom == null || obj.profileIdFrom == "") {
            validationErrors.add(RequiredFieldIsNotSet("profileIdFrom"))
        }
        if (obj?.profileIdTo == null || obj.profileIdTo == "") {
            validationErrors.add(RequiredFieldIsNotSet("profileIdTo"))
        }
        if (obj?.message == null || obj.message == "") {
            validationErrors.add(RequiredFieldIsNotSet("message"))
        }
        return if (validationErrors.isEmpty()) {
            ValidationResult.SUCCESS
        } else {
            ValidationResult(validationErrors)
        }
    }
}
