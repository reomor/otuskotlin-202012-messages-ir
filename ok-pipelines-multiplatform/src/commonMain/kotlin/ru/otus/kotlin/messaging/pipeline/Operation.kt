package ru.otus.kotlin.messaging.pipeline

class Operation<T> private constructor(
    private val checkPrecondition: T.() -> Boolean,
    private val runOperation: T.() -> Unit,
    private val handleEx: T.(Throwable) -> Unit
) : IOperation<T> {

    override suspend fun execute(context: T) {
        try {
            if (checkPrecondition(context)) runOperation(context)
        } catch (e: Exception) {
            handleEx(context, e)
        }
    }

    @PipelineDsl
    class Builder<T> : IOperationBuilder<T> {

        private var checkPrecondition: T.() -> Boolean = { true }
        private var runOperation: T.() -> Unit = { }
        private var handleEx: T.(Throwable) -> Unit = { e -> throw e }

        fun startIf(block: T.() -> Boolean) {
            checkPrecondition = block
        }

        fun execute(block: T.() -> Unit) {
            runOperation = block
        }

        fun onError(block: T.(Throwable) -> Unit) {
            handleEx = block
        }

        override fun build(): Operation<T> = Operation(checkPrecondition, runOperation, handleEx)
    }
}
