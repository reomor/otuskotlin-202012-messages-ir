package ru.otus.kotlin.messaging.api.validation

import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageDto
import ru.otus.kotlin.messaging.api.validation.validator.ChannelMessageValidator
import kotlin.test.Test
import kotlin.test.assertFalse

internal class ChannelMessageValidatorTest {

    @Test
    fun validationFail_noProfileIdAndNoMessage() {

        val messageDto = ChannelMessageDto(
            null,
            "123456789abc",
            ""
        )

        val validationResult = ChannelMessageValidator.validate(messageDto)

        assertFalse(validationResult.isSuccess)

        val errorMessages = validationResult.errors
            .map { error -> error.message }
            .toSet()

        errorMessages.contains("profileIdFrom")
        errorMessages.contains("message")
    }

    @Test
    fun validationFail_profileIdFromAndGroupTo_atTheSameTime() {

        val messageDto = ChannelMessageDto(
            "1234",
            "123456789abc",
            "4321",
            "1234"
        )

        val validationResult = ChannelMessageValidator.validate(messageDto)

        assertFalse(validationResult.isSuccess)

        val errorMessages = validationResult.errors
            .map { error -> error.message }
            .toSet()

        errorMessages.contains("The only one")
    }
}
