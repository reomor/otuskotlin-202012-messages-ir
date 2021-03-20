package ru.otus.kotlin.messaging.pipeline

interface IOperation<T> {
    suspend fun execute(context: T): Unit
}
