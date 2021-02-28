package ru.otus.kotlin.messaging

data class InstantMessage(
    val profileIdTo: ProfileId = ProfileId.NONE,
    override val id: MessageId = MessageId.NONE,
    override val profileIdFrom: ProfileId = ProfileId.NONE,
    override val messageText: String = "",
    override val resourceLinks: List<String> = emptyList()
) : Message()

data class ChannelMessage(
    val channelId: ChannelId = ChannelId.NONE,
    override val id: MessageId = MessageId.NONE,
    override val profileIdFrom: ProfileId = ProfileId.NONE,
    override val messageText: String = "",
    override val resourceLinks: List<String> = emptyList()
) : Message()

sealed class Message {
    abstract val id: MessageId
    abstract val profileIdFrom: ProfileId
    abstract val messageText: String
    abstract val resourceLinks: List<String>
}

inline class MessageId(val id: String) {
    companion object {
        val NONE = MessageId("")
    }
}
