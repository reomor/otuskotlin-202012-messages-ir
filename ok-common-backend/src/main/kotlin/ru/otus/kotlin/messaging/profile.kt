package ru.otus.kotlin.messaging

data class Profile(
    val id: ProfileId = ProfileId.NONE,
    val username: String = ""
)

inline class ProfileId(val id: String) {
    companion object {
        val NONE = ProfileId("")
    }
}
