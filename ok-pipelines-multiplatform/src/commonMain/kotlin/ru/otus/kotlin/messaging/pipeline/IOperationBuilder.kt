package ru.otus.kotlin.messaging.pipeline

interface IOperationBuilder<T> {
    fun build() : IOperation<T>
}
