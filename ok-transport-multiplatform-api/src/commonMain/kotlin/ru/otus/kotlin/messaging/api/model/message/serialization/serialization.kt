package ru.otus.kotlin.messaging.api.model.message.serialization

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import ru.otus.kotlin.messaging.api.model.common.AbstractRequest
import ru.otus.kotlin.messaging.api.model.common.AbstractResponse
import ru.otus.kotlin.messaging.api.model.common.Request
import ru.otus.kotlin.messaging.api.model.common.Response
import ru.otus.kotlin.messaging.api.model.message.*

val requestResponseSerializersModules = SerializersModule {

    fun PolymorphicModuleBuilder<AbstractRequest>.registerProjectSubclasses() {
        subclass(CreateChannelMessageRequest::class)
        subclass(DeleteChannelMessageRequest::class)
        subclass(EditChannelMessageRequest::class)
        subclass(GetChannelMessageRequest::class)
    }

    fun PolymorphicModuleBuilder<AbstractResponse>.registerProjectSubclasses() {
        subclass(CreateChannelMessageResponse::class)
        subclass(DeleteChannelMessageResponse::class)
        subclass(EditChannelMessageResponse::class)
        subclass(GetChannelMessageResponse::class)
    }

    polymorphic(Request::class) { registerProjectSubclasses() }
    polymorphic(Response::class) { registerProjectSubclasses() }
    polymorphic(AbstractRequest::class) { registerProjectSubclasses() }
    polymorphic(AbstractResponse::class) { registerProjectSubclasses() }
}

val requestResponseSerializer: Json by lazy {
    Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        serializersModule = requestResponseSerializersModules
        classDiscriminator = "type"
    }
}
