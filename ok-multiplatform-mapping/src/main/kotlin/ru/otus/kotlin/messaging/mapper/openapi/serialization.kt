package ru.otus.kotlin.messaging.mapper.openapi

import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlinx.serialization.modules.plus
import kotlinx.serialization.modules.polymorphic
import ru.otus.kotlin.messaging.api.model.message.serialization.requestResponseSerializersModules
import ru.otus.kotlin.messaging.openapi.channel.models.BaseMessage
import ru.otus.kotlin.messaging.openapi.channel.models.ChannelDto
import ru.otus.kotlin.messaging.openapi.channel.models.ChannelType
import ru.otus.kotlin.messaging.openapi.channel.models.CreateChannelRequest
import java.util.*

@ExperimentalSerializationApi
private val openApiRequestResponseSerializersModules = SerializersModule {
    polymorphic(BaseMessage::class) {
        subclass(CreateChannelRequest::class, CreateChannelRequestSerializer)
    }
    contextual(ChannelTypeSerializer)
    contextual(ChannelDtoSerializer)
    contextual(CreateChannelRequestSerializer)
    contextual(UUIDSerializer)
}

val generalRequestResponseSerializer: Json by lazy {
    Json {
        prettyPrint = true
//        isLenient = true
        ignoreUnknownKeys = true
        serializersModule = requestResponseSerializersModules.plus(openApiRequestResponseSerializersModules)
        classDiscriminator = "type"
    }
}

//object BaseMessageSerializer : KSerializer<BaseMessage> {
//
//    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("BaseMessage") {
//    }
//
//    override fun serialize(encoder: Encoder, value: BaseMessage) {
//        when (value.type) {
//            "CreateChannelRequest" -> Json.encodeToString(value as CreateChannelRequest)
//        }
//    }
//
//    override fun deserialize(decoder: Decoder): BaseMessage {
//        val string = decoder.decodeString()
//        return Json.decodeFromString(string)
//    }
//}

@ExperimentalSerializationApi
@Serializer(forClass = CreateChannelRequest::class)
object CreateChannelRequestSerializer : KSerializer<CreateChannelRequest> {

    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("CreateChannelRequest") {
            element<String>(elementName = "requestId")
            element<String>(elementName = "requestTime")
            element(elementName = "channel", ChannelDtoSerializer.descriptor)
        }

    override fun serialize(encoder: Encoder, value: CreateChannelRequest) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.requestId ?: "")
            encodeStringElement(descriptor, 1, value.requestTime ?: "")
            encodeNullableSerializableElement(descriptor, 2, ChannelDtoSerializer, value.channel)
        }
    }

    override fun deserialize(decoder: Decoder): CreateChannelRequest =
        decoder.decodeStructure(descriptor) {
            var requestId: String? = null
            var requestTime: String? = null
            var channel: ChannelDto? = null
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> requestId = decodeStringElement(descriptor, 1)
                    1 -> requestTime = decodeStringElement(descriptor, 2)
                    2 -> channel = decodeSerializableElement(descriptor, 2, ChannelDtoSerializer)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            CreateChannelRequest(
                type = "CreateChannelRequest",
                requestId = requestId,
                requestTime = requestTime,
                channel = channel
            )
        }
}

//Doesn't work 1.1.0
//@ExperimentalSerializationApi
//@Serializer(forClass = ChannelDto::class)
//object ChannelDtoSerializer

object ChannelDtoSerializer : KSerializer<ChannelDto> {

    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("ChannelDto") {
            element<String>(elementName = "id")
            element<String>(elementName = "name")
            element<String>(elementName = "ownerId")
            element<ChannelType>(elementName = "type")
        }

    @ExperimentalSerializationApi
    override fun serialize(encoder: Encoder, value: ChannelDto) =
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.id ?: "")
            encodeStringElement(descriptor, 1, value.name ?: "")
            encodeStringElement(descriptor, 2, value.ownerId ?: "")
            encodeNullableSerializableElement(descriptor, 3, ChannelTypeSerializer, value.type)
        }

    override fun deserialize(decoder: Decoder): ChannelDto =
        decoder.decodeStructure(descriptor) {
            var id: String? = null
            var name: String? = null
            var ownerId: String? = null
            var channelType: ChannelType? = null
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> id = decodeStringElement(descriptor, 0)
                    1 -> name = decodeStringElement(descriptor, 1)
                    2 -> ownerId = decodeStringElement(descriptor, 2)
                    3 -> channelType = decodeSerializableElement(descriptor, 2, ChannelTypeSerializer)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            ChannelDto(
                id = id,
                name = name,
                ownerId = ownerId,
                type = channelType
            )
        }
}

object UUIDSerializer : KSerializer<UUID> {
    private val serializer = String.serializer()

    override val descriptor = serializer.descriptor

    override fun serialize(encoder: Encoder, value: UUID) =
        serializer.serialize(encoder, value.toString())

    override fun deserialize(decoder: Decoder) =
        UUID.fromString(serializer.deserialize(decoder))
}

//@ExperimentalSerializationApi
//@Serializer(forClass = ChannelType::class)
object ChannelTypeSerializer : KSerializer<ChannelType> {
    private val serializer = String.serializer()

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("ChannelType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ChannelType) =
        serializer.serialize(encoder, value.name)

    override fun deserialize(decoder: Decoder): ChannelType =
        ChannelType.valueOf(serializer.deserialize(decoder))
}
