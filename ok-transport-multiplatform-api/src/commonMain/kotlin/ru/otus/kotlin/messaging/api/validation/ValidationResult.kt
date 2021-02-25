package ru.otus.kotlin.messaging.api.validation

class ValidationResult(val errors: List<ValidationError>) {

    val isSuccess = errors.isEmpty()

    companion object {
        val SUCCESS = ValidationResult(emptyList())
    }
}
