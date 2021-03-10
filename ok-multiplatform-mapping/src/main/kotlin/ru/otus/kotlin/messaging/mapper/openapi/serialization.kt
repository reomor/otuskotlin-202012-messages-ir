package ru.otus.kotlin.messaging.mapper.openapi

import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlinx.serialization.modules.plus
import kotlinx.serialization.modules.polymorphic
import ru.otus.kotlin.messaging.api.model.message.serialization.requestResponseSerializersModules
import ru.otus.kotlin.messaging.openapi.channel.models.*
import java.util.*

@ExperimentalSerializationApi
private val openApiRequestResponseSerializersModules = SerializersModule {
    polymorphic(BaseMessage::class) {
        subclass(CreateChannelRequest::class, CreateChannelRequestSerializer)
        subclass(CreateChannelResponse::class, CreateChannelResponseSerializer)
    }
    contextual(ChannelTypeSerializer)
    contextual(ChannelDtoSerializer)
    contextual(ResponseStatusSerializer)
    contextual(ErrorSeveritySerializer)
    contextual(ErrorDtoSerializer)
    contextual(CreateChannelRequestSerializer)
    contextual(CreateChannelResponseSerializer)
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
                    0 -> requestId = decodeStringElement(descriptor, index)
                    1 -> requestTime = decodeStringElement(descriptor, index)
                    2 -> channel = decodeSerializableElement(descriptor, index, ChannelDtoSerializer)
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

@ExperimentalSerializationApi
@Serializer(forClass = CreateChannelResponse::class)
object CreateChannelResponseSerializer : KSerializer<CreateChannelResponse> {

    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("CreateChannelResponse") {
            element<String>(elementName = "responseId")
            element<String>(elementName = "responseTime")
            element(elementName = "errors", ListSerializer(ErrorDtoSerializer).descriptor)
            element(elementName = "status", ResponseStatusSerializer.descriptor)
            element(elementName = "request", CreateChannelRequestSerializer.descriptor)
            element(elementName = "channel", ChannelDtoSerializer.descriptor)
        }

    override fun serialize(encoder: Encoder, value: CreateChannelResponse) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.responseId ?: "")
            encodeStringElement(descriptor, 1, value.responseTime ?: "")
            encodeNullableSerializableElement(descriptor, 2, ListSerializer(ErrorDtoSerializer), value.errors)
            encodeNullableSerializableElement(descriptor, 3, ResponseStatusSerializer, value.status)
            encodeNullableSerializableElement(descriptor, 4, CreateChannelRequestSerializer, value.request as CreateChannelRequest)
            encodeNullableSerializableElement(descriptor, 5, ChannelDtoSerializer, value.channel)
        }
    }

    override fun deserialize(decoder: Decoder): CreateChannelResponse =
        decoder.decodeStructure(descriptor) {
            var responseId: String? = null
            var responseTime: String? = null
            var errors: List<ErrorDto> = emptyList()
            var status: ResponseStatus? = null
            var request: BaseMessage? = null
            var channel: ChannelDto? = null
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> responseId = decodeStringElement(descriptor, index)
                    1 -> responseTime = decodeStringElement(descriptor, index)
                    2 -> errors = decodeSerializableElement(descriptor, index, ListSerializer(ErrorDtoSerializer))
                    3 -> status = decodeSerializableElement(descriptor, index, ResponseStatusSerializer)
                    4 -> request = decodeSerializableElement(descriptor, index, CreateChannelRequestSerializer)
                    5 -> channel = decodeSerializableElement(descriptor, index, ChannelDtoSerializer)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            CreateChannelResponse(
                type = "CreateChannelResponse",
                responseId = responseId,
                responseTime = responseTime,
                errors = errors,
                status = status,
                request = request,
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

object ResponseStatusSerializer : KSerializer<ResponseStatus> {
    private val serializer = String.serializer()

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("ResponseStatus", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ResponseStatus) =
        serializer.serialize(encoder, value.name)

    override fun deserialize(decoder: Decoder): ResponseStatus =
        ResponseStatus.valueOf(serializer.deserialize(decoder))
}

object ErrorSeveritySerializer : KSerializer<ErrorSeverity> {
    private val serializer = String.serializer()

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("ErrorSeverity", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ErrorSeverity) =
        serializer.serialize(encoder, value.name)

    override fun deserialize(decoder: Decoder): ErrorSeverity =
        ErrorSeverity.valueOf(serializer.deserialize(decoder))
}

object ErrorDtoSerializer : KSerializer<ErrorDto> {

    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("ErrorDto") {
            element<String>(elementName = "code")
            element<ErrorSeverity>(elementName = "level")
            element<String>(elementName = "message")
        }

    @ExperimentalSerializationApi
    override fun serialize(encoder: Encoder, value: ErrorDto) =
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.code ?: "")
            encodeNullableSerializableElement(descriptor, 1, ErrorSeveritySerializer, value.level)
            encodeStringElement(descriptor, 2, value.message ?: "")
        }

    override fun deserialize(decoder: Decoder): ErrorDto =
        decoder.decodeStructure(descriptor) {
            var code: String? = null
            var level: ErrorSeverity? = null
            var message: String? = null
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> code = decodeStringElement(descriptor, 0)
                    1 -> level = decodeSerializableElement(descriptor, 1, ErrorSeveritySerializer)
                    2 -> message = decodeStringElement(descriptor, 2)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            ErrorDto(
                code = code,
                level = level,
                message = message
            )
        }
}

