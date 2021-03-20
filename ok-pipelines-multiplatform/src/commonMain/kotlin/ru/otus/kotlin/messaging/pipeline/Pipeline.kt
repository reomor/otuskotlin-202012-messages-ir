package ru.otus.kotlin.messaging.pipeline

class Pipeline<T> private constructor(
    private val checkPrecondition: T.() -> Boolean,
    private val operations: List<IOperation<T>>,
    private val handleEx: T.(Throwable) -> Unit
) : IOperation<T> {

    override suspend fun execute(context: T) {
        try {
            if (checkPrecondition.invoke(context)) {
                operations.forEach { operation -> operation.execute(context) }
            }
        } catch (e: Exception) {
            handleEx.invoke(context, e)
        }
    }

    class Builder<T> : IOperationBuilder<T> {

        private var checkPrecondition: T.() -> Boolean = { true }
        private var operations: MutableList<IOperation<T>> = mutableListOf()
        private var handleEx: T.(Throwable) -> Unit = { e -> throw e }

        fun startIf(block: T.() -> Boolean): Unit {
            checkPrecondition = block
        }

        fun execute(operation: Operation<T>): Unit {
            operations.add(operation)
        }

        fun onError(block: T.(Throwable) -> Unit): Unit {
            handleEx = block
        }

        override fun build(): Pipeline<T> = Pipeline(checkPrecondition, operations, handleEx)
    }
}
