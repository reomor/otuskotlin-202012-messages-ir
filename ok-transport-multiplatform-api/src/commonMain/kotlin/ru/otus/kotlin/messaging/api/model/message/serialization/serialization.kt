package ru.otus.kotlin.messaging.api.model.message.serialization

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import ru.otus.kotlin.messaging.api.model.common.AbstractRequest
import ru.otus.kotlin.messaging.api.model.common.AbstractResponse
import ru.otus.kotlin.messaging.api.model.common.Request
import ru.otus.kotlin.messaging.api.model.common.Response
import ru.otus.kotlin.messaging.api.model.message.*

private val requestResponseSerializersModules = SerializersModule {

    fun PolymorphicModuleBuilder<AbstractRequest>.registerProjectSubclasses() {
        subclass(CreateChannelMessageRequest::class, CreateChannelMessageRequest.serializer())
        subclass(DeleteChannelMessageRequest::class, DeleteChannelMessageRequest.serializer())
        subclass(EditChannelMessageRequest::class, EditChannelMessageRequest.serializer())
        subclass(GetChannelMessageRequest::class, GetChannelMessageRequest.serializer())
    }

    fun PolymorphicModuleBuilder<AbstractResponse>.registerProjectSubclasses() {
        subclass(CreateChannelMessageResponse::class, CreateChannelMessageResponse.serializer())
        subclass(DeleteChannelMessageResponse::class, DeleteChannelMessageResponse.serializer())
        subclass(EditChannelMessageResponse::class, EditChannelMessageResponse.serializer())
        subclass(GetChannelMessageResponse::class, GetChannelMessageResponse.serializer())
    }

    polymorphic(Request::class) { registerProjectSubclasses() }
    polymorphic(Response::class) { registerProjectSubclasses() }
    polymorphic(AbstractRequest::class) { registerProjectSubclasses() }
    polymorphic(AbstractResponse::class) { registerProjectSubclasses() }
}

val requestResponseSerializer = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
    serializersModule = requestResponseSerializersModules
    classDiscriminator = "type"
}
