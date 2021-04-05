package ru.otus.kotlin.messaging.app.ktor.controller

import io.ktor.http.*

object Common{
    val jsonContentTypeString = "application/json"
    val jsonContentType = ContentType.parse(jsonContentTypeString)
}
