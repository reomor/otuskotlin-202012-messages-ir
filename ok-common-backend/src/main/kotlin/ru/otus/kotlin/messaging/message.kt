package ru.otus.kotlin.messaging

data class InstantMessage(
    val id: MessageId = MessageId.NONE,
    val profileIdFrom: ProfileId = ProfileId.NONE,
    val profileIdTo: ProfileId = ProfileId.NONE,
    val message: String = "",
    val resourceLinks: List<String> = emptyList()
)

data class ChannelMessage(
    val id: MessageId = MessageId.NONE,
    val profileIdFrom: ProfileId = ProfileId.NONE,
    val channelId: ChannelId = ChannelId.NONE,
    val message: String = "",
    val resourceLinks: List<String> = emptyList()
)

inline class MessageId(val id: String) {
    companion object {
        val NONE = MessageId("")
    }
}

