package ru.otus.kotlin.messaging

enum class ErrorSeverity {
    INFO,
    WARN,
    FATAL;
}

data class Error(
    val code: String = "",
    val level: ErrorSeverity = ErrorSeverity.INFO,
    val message: String = ""
)
