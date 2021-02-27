package ru.otus.kotlin.messaging.api.validation.validator

import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageDto
import ru.otus.kotlin.messaging.api.model.message.dto.ChannelMessageFilter
import ru.otus.kotlin.messaging.api.validation.ValidationError
import ru.otus.kotlin.messaging.api.validation.ValidationResult
import ru.otus.kotlin.messaging.api.validation.Validator
import ru.otus.kotlin.messaging.api.validation.error.FieldBreaksConstraint
import ru.otus.kotlin.messaging.api.validation.error.OnlyOneFieldMustBeSet
import ru.otus.kotlin.messaging.api.validation.error.ObjectIsNull
import ru.otus.kotlin.messaging.api.validation.error.RequiredFieldIsNotSet


object ChannelMessageValidator : Validator<ChannelMessageDto?> {

    override fun validate(obj: ChannelMessageDto?): ValidationResult {

        val validationErrors = mutableListOf<ValidationError>()

        checkNullableObject(obj, "ChannelMessageDto", validationErrors)
        checkStringParam(obj?.profileIdFrom, "profileIdFrom", validationErrors)
        checkStringParam(obj?.message, "message", validationErrors)

        val profileIdTo = obj?.profileIdTo
        val groupIdTo = obj?.channelIdTo

        if ((profileIdTo == null && groupIdTo == null) || (profileIdTo != null && groupIdTo != null)) {
            validationErrors.add(OnlyOneFieldMustBeSet("profileIdTo", "groupIdTo"))
        }

        if (profileIdTo != null) {
            checkStringParam(obj.profileIdTo, "profileIdTo", validationErrors)
        } else {
            checkStringParam(obj?.channelIdTo, "channelIdTo", validationErrors)
        }

        return validationResult(validationErrors)
    }
}

object ChannelMessageFilterValidator : Validator<ChannelMessageFilter?> {

    override fun validate(obj: ChannelMessageFilter?): ValidationResult {

        val validationErrors = mutableListOf<ValidationError>()

        checkNullableObject(obj, "ChannelMessageFilter", validationErrors)
        checkStringParam(obj?.channelId, "channelId", validationErrors)

        if (obj != null) {
            checkLimits(obj.pageNumber, 0, "pageNumber", validationErrors)
            checkLimits(obj.pageSize, 1, "pageNumber", validationErrors)
        }

        return validationResult(validationErrors)
    }
}

private fun checkNullableObject(
    obj: Any?, objName:
    String,
    validationErrors: MutableList<ValidationError>
) {
    if (obj == null) {
        validationErrors.add(ObjectIsNull(objName))
    }
}

private fun checkStringParam(
    param: String?,
    paramName: String,
    validationErrors: MutableList<ValidationError>
) {
    if (param == null || param == "") {
        validationErrors.add(RequiredFieldIsNotSet(paramName))
    }
}

private fun <T : Comparable<T>> checkLimits(
    param: T,
    limit: T,
    paramName: String,
    validationErrors: MutableList<ValidationError>
) {
    if (param < limit) {
        validationErrors.add(FieldBreaksConstraint(paramName, limit.toString()))
    }
}

private fun validationResult(validationErrors: List<ValidationError>): ValidationResult {
    return if (validationErrors.isEmpty()) {
        ValidationResult.SUCCESS
    } else {
        ValidationResult(validationErrors)
    }
}
