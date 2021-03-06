package ru.otus.kotlin.messaging.app.spring

import org.springframework.fu.kofu.webApplication
import org.springframework.fu.kofu.webmvc.webMvc
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.KotlinSerializationJsonHttpMessageConverter
import ru.otus.kotlin.messaging.api.model.message.serialization.requestResponseSerializer
import ru.otus.kotlin.messaging.app.spring.controller.MessagingController

object MessagingApi {
    const val createMessageUri = "/messaging/create"
}

val app = webApplication {

    beans {
        bean<MessagingController>()
        // need to create customized bean for polymorphic serialization
        bean<HttpMessageConverter<Any>>(name = "kotlinSerializationJsonHttpMessageConverter") {
            KotlinSerializationJsonHttpMessageConverter(requestResponseSerializer)
        }
    }

    webMvc {
        port = if (profiles.contains("test")) 8181 else 8080

        router {
            val messagingController = ref<MessagingController>()
            POST(MessagingApi.createMessageUri, messagingController::create)
        }

        converters {
            string()
//            use customized bean instead of it
//            kotlinSerialization()
        }
    }
}

fun main() {
    app.run()
}
