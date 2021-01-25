package ru.otus.kotlin.dsl;

import ru.otus.kotlin.domain.*

@DslMarker
annotation class UserDslMarker

@UserDslMarker
abstract class UserDslBuilder

@UserDslMarker
class UserDsl {

    private var userInfoBuilder: UserInfoBuilder = UserInfoBuilder()
    private var email: Email = Email.NONE
    private var phone: Phone = NoPhone
    private var roles: Set<Role> = emptySet()
    private var scopes: Set<Scope> = emptySet()

    fun name(block: UserInfoBuilder.() -> Unit): UserInfoBuilder = UserInfoBuilder().apply(block)
        .also { userInfoBuilder = it }

    fun contacts(block: ContactsBuilder.() -> Unit): ContactsBuilder = ContactsBuilder().apply(block)
        .also { builder ->
            email = Email(builder.email)
            phone = StringPhone(builder.phone)
        }

    fun roles(block: RoleBuilder.() -> Unit): RoleBuilder = RoleBuilder().apply(block)
        .also { builder -> roles = builder.roles }

    fun scopes(block: ScopeBuilder.() -> Unit): ScopeBuilder = ScopeBuilder().apply(block)
        .also { builder -> scopes = builder.scopes }

    fun build() = UserModel(
        firstName = userInfoBuilder.first,
        middleName = userInfoBuilder.middle,
        lastName = userInfoBuilder.last,
        email = email,
        phone = phone,
        roles = roles,
        scopes = scopes
    )

    companion object {
        fun user(block: UserDsl.() -> Unit) = UserDsl().apply(block).build()
    }
}

data class UserInfoBuilder(
    var first: String = "",
    var middle: String = "",
    var last: String = ""
) : UserDslBuilder()

data class ContactsBuilder(
    var email: String = "",
    var phone: String = ""
) : UserDslBuilder()

class RoleBuilder : UserDslBuilder() {
    private val _roles = mutableSetOf<Role>()
    val roles: Set<Role>
        get() = _roles.toSet()

    fun add(role: Role): Boolean = _roles.add(role)
    operator fun Role.unaryPlus() = add(this)
}

class ScopeBuilder : UserDslBuilder() {
    private val _scopes = mutableSetOf<Scope>()
    val scopes: Set<Scope>
        get() = _scopes.toSet()

    fun add(scope: String) = _scopes.add(Scope(scope))
}
