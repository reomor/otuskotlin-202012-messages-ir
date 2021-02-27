package ru.otus.kotlin.messaging.api.validation

class ValidationResult(val errors: List<ValidationError>) {

    val isSuccess = errors.isEmpty()

    val errorMessages = errors.map { error -> error.message }.toList()

    companion object {
        val SUCCESS = ValidationResult(emptyList())
    }
}
