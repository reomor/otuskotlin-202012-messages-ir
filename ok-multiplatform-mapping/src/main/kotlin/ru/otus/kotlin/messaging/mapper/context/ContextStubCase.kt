package ru.otus.kotlin.messaging.mapper.context

enum class ContextStubCase {
    NONE,

    MESSAGE_CREATE_SUCCESS,
    MESSAGE_DELETE_SUCCESS,
    MESSAGE_EDIT_SUCCESS,
    MESSAGE_GET_SUCCESS,

    CHANNEL_CREATE_SUCCESS,
    CHANNEL_DELETE_SUCCESS,
    CHANNEL_GET_SUCCESS
}
