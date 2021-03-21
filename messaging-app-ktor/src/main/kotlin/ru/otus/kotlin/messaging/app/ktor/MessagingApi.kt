package ru.otus.kotlin.messaging.app.ktor

object MessagingApi {
    const val baseUri = "/messaging"
    const val createMessageUri = "/create"
    const val deleteMessageUri = "/delete"
    const val editMessageUri = "/edit"
    const val getMessageUri = "/get"
}

object ChannelApi {
    const val baseUri = "/channel"
    const val createChannelUri = "/create"
    const val deleteChannelUri = "/delete"
    const val getChannelUri = "/get"
}
