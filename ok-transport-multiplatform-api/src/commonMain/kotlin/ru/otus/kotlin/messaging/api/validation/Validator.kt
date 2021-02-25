package ru.otus.kotlin.messaging.api.validation

interface Validator<T> {
    fun validate(obj: T): ValidationResult
}
