package ru.otus.kotlin.messaging.pipeline

inline fun <T> operation(block: Operation.Builder<T>.() -> Unit): Operation<T> =
    Operation.Builder<T>()
        .apply(block)
        .build()

inline fun <T> pipeline(block: Pipeline.Builder<T>.() -> Unit): Pipeline<T> =
    Pipeline.Builder<T>()
        .apply(block)
        .build()
