package ru.otus.kotlin.coroutine.continuation

interface CalculationContext

object EmptyCalculationContext : CalculationContext

interface CalculationContinuation<T> {
    val context: CalculationContext
    fun calculate(result: T): Unit
}

fun <T> CalculationContext.continuation(resume: CalculationContext.(T) -> Unit) = object : CalculationContinuation<T> {
    override val context: CalculationContext
        get() = this@continuation

    override fun calculate(result: T) = this@continuation.resume(result)
}

fun multiplication(a: Int, b: Int, continuation: CalculationContinuation<Int>) = continuation.calculate(a * b)

fun main() {
    with(EmptyCalculationContext) {
        multiplication(4, 5, continuation { result ->
            println(result)
        })
    }
}
