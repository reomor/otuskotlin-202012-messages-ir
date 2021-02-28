package ru.otus.kotlin.messaging.context

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.otus.kotlin.messaging.*

class MessagingContext(
    var messageIds: List<MessageId> = emptyList(),
    var channelIds: List<ChannelId> = emptyList(),
    var channelId: ChannelId = ChannelId.NONE,
    var profileIdFrom: ProfileId = ProfileId.NONE,
    var profileIdTo: ProfileId = ProfileId.NONE,
    var messages: List<Message> = emptyList(),
    var channels: List<Channel> = emptyList(),
    var errors: List<Error> = emptyList(),
    private var _page: Page? = null,
    private var _channel: Channel? = null,
    private var _instantMessage: InstantMessage? = null,
    private var _channelMessage: ChannelMessage? = null,
    private var _profile: Profile? = null
) {
    var page
        get() = _page ?: throw IllegalArgumentException("page is not set")
        set(value) {
            _page = value
        }

    var channel
        get() = _channel ?: throw IllegalArgumentException("channel is not set")
        set(value) {
            _channel = value
        }

    var instantMessage
        get() = _instantMessage ?: throw IllegalArgumentException("instantMessage is not set")
        set(value) {
            _instantMessage = value
        }

    var channelMessage
        get() = _channelMessage ?: throw IllegalArgumentException("channelMessage is not set")
        set(value) {
            _channelMessage = value
        }

    var profile
        get() = _profile ?: throw IllegalArgumentException("profile is not set")
        set(value) {
            _profile = value
        }

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        val log: Logger = LoggerFactory.getLogger(javaClass.enclosingClass)
    }
}
