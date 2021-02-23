package ru.otus.kotlin.messaging.api.model.message.serialization

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import ru.otus.kotlin.messaging.api.model.common.AbstractRequest
import ru.otus.kotlin.messaging.api.model.common.AbstractResponse
import ru.otus.kotlin.messaging.api.model.common.Request
import ru.otus.kotlin.messaging.api.model.common.Response
import ru.otus.kotlin.messaging.api.model.message.CreatePersonalMessageRequest
import ru.otus.kotlin.messaging.api.model.message.CreatePersonalMessageResponse

val requestResponseSerializer = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
    serializersModule = SerializersModule {

        fun PolymorphicModuleBuilder<AbstractRequest>.registerProjectSubclasses() {
            subclass(CreatePersonalMessageRequest::class, CreatePersonalMessageRequest.serializer())
        }

        fun PolymorphicModuleBuilder<AbstractResponse>.registerProjectSubclasses() {
            subclass(CreatePersonalMessageResponse::class, CreatePersonalMessageResponse.serializer())
        }

        polymorphic(Request::class) { registerProjectSubclasses() }
        polymorphic(AbstractRequest::class) { registerProjectSubclasses() }
        polymorphic(Response::class) { registerProjectSubclasses() }
        polymorphic(AbstractResponse::class) { registerProjectSubclasses() }
    }
    classDiscriminator = "type"
}
