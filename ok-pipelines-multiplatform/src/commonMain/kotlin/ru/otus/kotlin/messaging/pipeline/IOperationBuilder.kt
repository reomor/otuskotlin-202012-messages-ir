package ru.otus.kotlin.messaging.pipeline

@PipelineDsl
interface IOperationBuilder<T> {
    fun build() : IOperation<T>
}
