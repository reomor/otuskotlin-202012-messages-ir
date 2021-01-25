package ru.otus.kotlin.domain

data class UserModel(
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val email: Email,
    val phone: Phone,
    val roles: Set<Role>,
    val scopes: Set<Scope>
)

inline class Email(val email: String) {
    companion object {
        val NONE: Email = Email("");
    }
}

sealed class Phone
object NoPhone : Phone()
data class StringPhone(val phone: String) : Phone()

enum class Role {
    USER,
    ADMIN,
    ANONYMOUS
}

data class Scope(val scope: String)
