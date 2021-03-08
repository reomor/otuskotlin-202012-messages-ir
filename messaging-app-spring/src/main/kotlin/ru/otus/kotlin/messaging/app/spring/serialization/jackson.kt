package ru.otus.kotlin.messaging.app.spring.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import ru.otus.kotlin.messaging.openapi.channel.models.CreateChannelRequest
import ru.otus.kotlin.messaging.openapi.channel.models.CreateChannelResponse

object CreateChannelRequestJsonDeserializer : JsonDeserializer<CreateChannelRequest>() {
    override fun deserialize(parser: JsonParser, context: DeserializationContext?): CreateChannelRequest {
        return parser.readValuesAs(CreateChannelRequest::class.java).next()
    }
}

object CreateChannelResponseJsonDeserializer : JsonDeserializer<CreateChannelResponse>() {
    override fun deserialize(parser: JsonParser, context: DeserializationContext?): CreateChannelResponse {
        return parser.readValuesAs(CreateChannelResponse::class.java).next()
    }
}
