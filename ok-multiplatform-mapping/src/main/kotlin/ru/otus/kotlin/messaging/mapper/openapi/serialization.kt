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
        subclass(DeleteChannelRequest::class, DeleteChannelRequestSerializer)
        subclass(DeleteChannelResponse::class, DeleteChannelResponseSerializer)
        subclass(GetChannelRequest::class, GetChannelRequestSerializer)
        subclass(GetChannelResponse::class, GetChannelResponseSerializer)
    }
    contextual(ChannelTypeSerializer)
    contextual(ChannelDtoSerializer)
    contextual(ChannelFilterDtoSerializer)
    contextual(ResponseStatusSerializer)
    contextual(ErrorSeveritySerializer)
    contextual(ErrorDtoSerializer)
    contextual(CreateChannelRequestSerializer)
    contextual(CreateChannelResponseSerializer)
    contextual(DeleteChannelRequestSerializer)
    contextual(DeleteChannelResponseSerializer)
    contextual(GetChannelRequestSerializer)
    contextual(GetChannelResponseSerializer)
}

val generalRequestResponseSerializer: Json by lazy {
    Json {
        prettyPrint = true
//        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
        serializersModule = requestResponseSerializersModules + openApiRequestResponseSerializersModules
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
            encodeNullableSerializableElement(
                descriptor,
                4,
                CreateChannelRequestSerializer,
                value.request as? CreateChannelRequest
            )
            encodeNullableSerializableElement(descriptor, 5, ChannelDtoSerializer, value.channel)
        }
    }

    override fun deserialize(decoder: Decoder): CreateChannelResponse =
        decoder.decodeStructure(descriptor) {

            var responseId: String? = null
            var responseTime: String? = null
            var errors: List<ErrorDto>? = null
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

@ExperimentalSerializationApi
@Serializer(forClass = DeleteChannelRequest::class)
object DeleteChannelRequestSerializer : KSerializer<DeleteChannelRequest> {

    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("DeleteChannelRequest") {
            element<String>(elementName = "requestId")
            element<String>(elementName = "requestTime")
            element<String>(elementName = "channelId")
        }

    override fun serialize(encoder: Encoder, value: DeleteChannelRequest) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.requestId ?: "")
            encodeStringElement(descriptor, 1, value.requestTime ?: "")
            encodeStringElement(descriptor, 2, value.channelId ?: "")
        }
    }

    override fun deserialize(decoder: Decoder): DeleteChannelRequest =
        decoder.decodeStructure(descriptor) {

            var requestId: String? = null
            var requestTime: String? = null
            var channelId: String? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> requestId = decodeStringElement(descriptor, index)
                    1 -> requestTime = decodeStringElement(descriptor, index)
                    2 -> channelId = decodeStringElement(descriptor, index)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            DeleteChannelRequest(
                type = "DeleteChannelRequest",
                requestId = requestId,
                requestTime = requestTime,
                channelId = channelId
            )
        }
}

@ExperimentalSerializationApi
@Serializer(forClass = DeleteChannelResponse::class)
object DeleteChannelResponseSerializer : KSerializer<DeleteChannelResponse> {

    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("DeleteChannelResponse") {
            element<String>(elementName = "responseId")
            element<String>(elementName = "responseTime")
            element(elementName = "errors", ListSerializer(ErrorDtoSerializer).descriptor)
            element(elementName = "status", ResponseStatusSerializer.descriptor)
            element(elementName = "request", CreateChannelRequestSerializer.descriptor)
            element(elementName = "channel", ChannelDtoSerializer.descriptor)
        }

    override fun serialize(encoder: Encoder, value: DeleteChannelResponse) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.responseId ?: "")
            encodeStringElement(descriptor, 1, value.responseTime ?: "")
            encodeNullableSerializableElement(descriptor, 2, ListSerializer(ErrorDtoSerializer), value.errors)
            encodeNullableSerializableElement(descriptor, 3, ResponseStatusSerializer, value.status)
            encodeNullableSerializableElement(
                descriptor,
                4,
                DeleteChannelRequestSerializer,
                value.request as? DeleteChannelRequest
            )
            encodeNullableSerializableElement(descriptor, 5, ChannelDtoSerializer, value.channel)
        }
    }

    override fun deserialize(decoder: Decoder): DeleteChannelResponse =
        decoder.decodeStructure(descriptor) {

            var responseId: String? = null
            var responseTime: String? = null
            var errors: List<ErrorDto>? = null
            var status: ResponseStatus? = null
            var request: BaseMessage? = null
            var channel: ChannelDto? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> responseId = decodeStringElement(descriptor, index)
                    1 -> responseTime = decodeStringElement(descriptor, index)
                    2 -> errors = decodeSerializableElement(descriptor, index, ListSerializer(ErrorDtoSerializer))
                    3 -> status = decodeSerializableElement(descriptor, index, ResponseStatusSerializer)
                    4 -> request = decodeSerializableElement(descriptor, index, DeleteChannelRequestSerializer)
                    5 -> channel = decodeSerializableElement(descriptor, index, ChannelDtoSerializer)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }

            DeleteChannelResponse(
                type = "DeleteChannelResponse",
                responseId = responseId,
                responseTime = responseTime,
                errors = errors,
                status = status,
                request = request,
                channel = channel
            )
        }
}

@ExperimentalSerializationApi
@Serializer(forClass = GetChannelRequest::class)
object GetChannelRequestSerializer : KSerializer<GetChannelRequest> {

    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("GetChannelRequest") {
            element<String>(elementName = "requestId")
            element<String>(elementName = "requestTime")
            element(elementName = "filter", ChannelFilterDtoSerializer.descriptor)
        }

    override fun serialize(encoder: Encoder, value: GetChannelRequest) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.requestId ?: "")
            encodeStringElement(descriptor, 1, value.requestTime ?: "")
            encodeNullableSerializableElement(descriptor, 2, ChannelFilterDtoSerializer, value.filter)
        }
    }

    override fun deserialize(decoder: Decoder): GetChannelRequest =
        decoder.decodeStructure(descriptor) {

            var requestId: String? = null
            var requestTime: String? = null
            var filter: ChannelFilterDto? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> requestId = decodeStringElement(descriptor, index)
                    1 -> requestTime = decodeStringElement(descriptor, index)
                    2 -> filter = decodeSerializableElement(descriptor, index, ChannelFilterDtoSerializer)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }

            GetChannelRequest(
                type = "GetChannelRequest",
                requestId = requestId,
                requestTime = requestTime,
                filter = filter
            )
        }
}

@ExperimentalSerializationApi
@Serializer(forClass = GetChannelResponse::class)
object GetChannelResponseSerializer : KSerializer<GetChannelResponse> {

    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("GetChannelResponse") {
            element<String>(elementName = "responseId")
            element<String>(elementName = "responseTime")
            element(elementName = "errors", ListSerializer(ErrorDtoSerializer).descriptor)
            element(elementName = "status", ResponseStatusSerializer.descriptor)
            element(elementName = "request", CreateChannelRequestSerializer.descriptor)
            element(elementName = "channel", ListSerializer(ChannelDtoSerializer).descriptor)
        }

    override fun serialize(encoder: Encoder, value: GetChannelResponse) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.responseId ?: "")
            encodeStringElement(descriptor, 1, value.responseTime ?: "")
            encodeNullableSerializableElement(descriptor, 2, ListSerializer(ErrorDtoSerializer), value.errors)
            encodeNullableSerializableElement(descriptor, 3, ResponseStatusSerializer, value.status)
            encodeNullableSerializableElement(
                descriptor,
                4,
                GetChannelRequestSerializer,
                value.request as? GetChannelRequest
            )
            encodeNullableSerializableElement(descriptor, 5, ListSerializer(ChannelDtoSerializer), value.channels)
        }
    }

    override fun deserialize(decoder: Decoder): GetChannelResponse =
        decoder.decodeStructure(descriptor) {

            var responseId: String? = null
            var responseTime: String? = null
            var errors: List<ErrorDto>? = null
            var status: ResponseStatus? = null
            var request: BaseMessage? = null
            var channels: List<ChannelDto>? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> responseId = decodeStringElement(descriptor, index)
                    1 -> responseTime = decodeStringElement(descriptor, index)
                    2 -> errors = decodeSerializableElement(descriptor, index, ListSerializer(ErrorDtoSerializer))
                    3 -> status = decodeSerializableElement(descriptor, index, ResponseStatusSerializer)
                    4 -> request = decodeSerializableElement(descriptor, index, GetChannelRequestSerializer)
                    5 -> channels = decodeSerializableElement(descriptor, index, ListSerializer(ChannelDtoSerializer))
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }

            GetChannelResponse(
                type = "GetChannelResponse",
                responseId = responseId,
                responseTime = responseTime,
                errors = errors,
                status = status,
                request = request,
                channels = channels
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
                    0 -> id = decodeStringElement(descriptor, index)
                    1 -> name = decodeStringElement(descriptor, index)
                    2 -> ownerId = decodeStringElement(descriptor, index)
                    3 -> channelType = decodeSerializableElement(descriptor, index, ChannelTypeSerializer)
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

object ChannelFilterDtoSerializer : KSerializer<ChannelFilterDto> {

    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("ChannelFilterDto") {
            element(elementName = "channelIds", ListSerializer(String.serializer()).descriptor)
            element<Int>(elementName = "pageSize")
            element<Int>(elementName = "pageNumber")
        }

    override fun serialize(encoder: Encoder, value: ChannelFilterDto) =
        encoder.encodeStructure(descriptor) {
            encodeNullableSerializableElement(
                descriptor,
                0,
                ListSerializer(String.serializer()),
                value.channelIds
            )
            encodeIntElement(descriptor, 1, value.pageSize ?: 0)
            encodeIntElement(descriptor, 2, value.pageNumber ?: 0)
        }

    override fun deserialize(decoder: Decoder): ChannelFilterDto =
        decoder.decodeStructure(descriptor) {

            var channelIds: List<String> = emptyList()
            var pageSize: Int? = null
            var pageNumber: Int? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> channelIds = decodeSerializableElement(descriptor, index, ListSerializer(String.serializer()))
                    1 -> pageSize = decodeIntElement(descriptor, index)
                    2 -> pageNumber = decodeIntElement(descriptor, index)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }

            ChannelFilterDto(
                channelIds = channelIds,
                pageSize = pageSize,
                pageNumber = pageNumber
            )
        }
}

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
                    0 -> code = decodeStringElement(descriptor, index)
                    1 -> level = decodeSerializableElement(descriptor, index, ErrorSeveritySerializer)
                    2 -> message = decodeStringElement(descriptor, index)
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
