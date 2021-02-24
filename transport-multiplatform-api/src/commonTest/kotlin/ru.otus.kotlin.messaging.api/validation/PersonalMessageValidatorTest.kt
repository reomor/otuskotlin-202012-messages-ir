package ru.otus.kotlin.messaging.api.validation

import ru.otus.kotlin.messaging.api.model.message.dto.PersonalMessageDto
import ru.otus.kotlin.messaging.api.validation.validator.PersonalMessageValidator
import kotlin.test.Test
import kotlin.test.assertFalse

internal class PersonalMessageValidatorTest {

    @Test
    fun validationFail() {

        val messageDto = PersonalMessageDto(
            null,
            "123456789abc",
            ""
        )

        val validationResult = PersonalMessageValidator.validate(messageDto)

        assertFalse(validationResult.isSuccess)

        val errorMessages = validationResult.errors
            .map { error -> error.message }
            .toSet()

        errorMessages.contains("profileIdFrom")
        errorMessages.contains("message")
    }
}
