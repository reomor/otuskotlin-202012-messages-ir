package ru.otus.kotlin.messaging.app.spring

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.fu.kofu.webApplication
import org.springframework.fu.kofu.webmvc.webMvc
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.KotlinSerializationJsonHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import ru.otus.kotlin.messaging.app.spring.controller.ChannelController
import ru.otus.kotlin.messaging.app.spring.controller.MessagingController
import ru.otus.kotlin.messaging.mapper.openapi.generalRequestResponseSerializer
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import ru.otus.kotlin.messaging.app.spring.serialization.*
import ru.otus.kotlin.messaging.openapi.channel.models.BaseMessage

object MessagingApi {
    const val createMessageUri = "/messaging/create"
    const val deleteMessageUri = "/messaging/delete"
    const val editMessageUri = "/messaging/edit"
    const val getMessageUri = "/messaging/get"
}

object ChannelApi {
    const val createChannelUri = "/channel/create"
    const val deleteChannelUri = "/channel/delete"
    const val getChannelUri = "/channel/get"
}

val app = webApplication {

    beans {
        bean<MessagingController>()
        bean<ChannelController>()
        // need to create customized bean for polymorphic serialization
        bean<HttpMessageConverter<Any>>(name = "kotlinSerializationJsonHttpMessageConverter") {
            KotlinSerializationJsonHttpMessageConverter(generalRequestResponseSerializer)
        }
        bean<HttpMessageConverter<Any>>(name = "mappingJackson2HttpMessageConverter") {
            val mapper = Jackson2ObjectMapperBuilder()
                .deserializerByType(BaseMessage::class.java, CreateChannelRequestJsonDeserializer)
                .deserializerByType(BaseMessage::class.java, CreateChannelResponseJsonDeserializer)
                .deserializerByType(BaseMessage::class.java, DeleteChannelRequestJsonDeserializer)
                .deserializerByType(BaseMessage::class.java, DeleteChannelResponseJsonDeserializer)
                .deserializerByType(BaseMessage::class.java, GetChannelRequestJsonDeserializer)
                .deserializerByType(BaseMessage::class.java, GetChannelResponseJsonDeserializer)
                .build<ObjectMapper>()
                .registerModule(KotlinModule())
            MappingJackson2HttpMessageConverter(mapper)
        }
    }

    webMvc {
        port = if (profiles.contains("test")) 8181 else 8080

        router {
            val messagingController = ref<MessagingController>()

            POST(MessagingApi.createMessageUri, messagingController::create)
            POST(MessagingApi.deleteMessageUri, messagingController::delete)
            POST(MessagingApi.editMessageUri, messagingController::edit)
            POST(MessagingApi.getMessageUri, messagingController::get)

            val channelController = ref<ChannelController>()
            POST(ChannelApi.createChannelUri, channelController::create)
            POST(ChannelApi.deleteChannelUri, channelController::delete)
            POST(ChannelApi.getChannelUri, channelController::get)
        }

        converters {
            string()
            //use customized bean instead of it
//            jackson{
//            }
            //use customized bean instead of it
//            kotlinSerialization()
        }
    }
}

fun main() {
    app.run()
}
