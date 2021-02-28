package ru.otus.kotlin.messaging.api.validation

import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageFilter
import ru.otus.kotlin.messaging.api.validation.validator.ChannelMessageFilterValidator
import kotlin.test.Test
import kotlin.test.assertFalse

internal class ChannelMessageFilterValidatorTest {

    @Test
    fun validationFail_noChannelIdAndBadPageNumber() {
        val filter = ChannelMessageFilter(
            channelId = null,
            pageNumber = -1,
            pageSize = 1
        )

        val validationResult = ChannelMessageFilterValidator.validate(filter)

        assertFalse(validationResult.isSuccess)

        val errorMessages = validationResult.errors
            .map { error -> error.message }
            .toSet()

        errorMessages.contains("channelId")
        errorMessages.contains("constraint")
    }

    @Test
    fun validationFail_badPageSize() {
        val filter = ChannelMessageFilter(
            pageNumber = 0,
            pageSize = 0
        )

        val validationResult = ChannelMessageFilterValidator.validate(filter)

        assertFalse(validationResult.isSuccess)

        val errorMessages = validationResult.errors
            .map { error -> error.message }
            .toSet()

        errorMessages.contains("constraint")
        errorMessages.contains("pageSize")
    }
}
