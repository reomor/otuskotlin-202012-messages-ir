package ru.otus.kotlin.messaging.app.spring.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import ru.otus.kotlin.messaging.openapi.channel.models.*

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

object DeleteChannelRequestJsonDeserializer : JsonDeserializer<DeleteChannelRequest>() {
    override fun deserialize(parser: JsonParser, context: DeserializationContext?): DeleteChannelRequest {
        return parser.readValuesAs(DeleteChannelRequest::class.java).next()
    }
}

object DeleteChannelResponseJsonDeserializer : JsonDeserializer<DeleteChannelResponse>() {
    override fun deserialize(parser: JsonParser, context: DeserializationContext?): DeleteChannelResponse {
        return parser.readValuesAs(DeleteChannelResponse::class.java).next()
    }
}

object GetChannelRequestJsonDeserializer : JsonDeserializer<GetChannelRequest>() {
    override fun deserialize(parser: JsonParser, context: DeserializationContext?): GetChannelRequest {
        return parser.readValuesAs(GetChannelRequest::class.java).next()
    }
}

object GetChannelResponseJsonDeserializer : JsonDeserializer<GetChannelResponse>() {
    override fun deserialize(parser: JsonParser, context: DeserializationContext?): GetChannelResponse {
        return parser.readValuesAs(GetChannelResponse::class.java).next()
    }
}
