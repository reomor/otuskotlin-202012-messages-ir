package ru.otus.kotlin.messaging

enum class ChannelType {
    PUBLIC_CHANNEL,
    PRIVATE_CHANNEL,
    UNDEFINED
}

data class Channel(
    val channelId: ChannelId = ChannelId.NONE,
    val name: String = "",
    val ownerId: ProfileId = ProfileId.NONE,
    val type: ChannelType = ChannelType.UNDEFINED
)

inline class ChannelId(val id: String) {
    companion object {
        val NONE = ChannelId("")
    }
}
