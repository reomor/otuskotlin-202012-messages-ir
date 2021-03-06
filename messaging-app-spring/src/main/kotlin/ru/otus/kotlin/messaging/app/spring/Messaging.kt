package ru.otus.kotlin.messaging.app.spring

import org.springframework.fu.kofu.webApplication
import org.springframework.fu.kofu.webmvc.webMvc

val app = webApplication {

    beans {

    }

    webMvc {
        converters {
            string()
            kotlinSerialization()
        }
    }
}

fun main() {
    app.run()
}
