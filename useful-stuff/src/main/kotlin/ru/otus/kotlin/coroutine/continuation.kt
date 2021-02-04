package ru.otus.kotlin.coroutine

interface Context

object EmptyContext : Context

interface Continuation<T> {
    val context: Context
    fun resumeWith(result: T)
}

fun <T> Context.continuation(resume: Context.(T) -> Unit) = object : Continuation<T> {
    override val context: Context
        get() = this@continuation

    override fun resumeWith(result: T) = this@continuation.resume(result)
}

//class printContinuation : Continuation<Unit> {
//    override val context: Context
//        get() =
//
//    override fun resumeWith(result: Unit) {
//        TODO("Not yet implemented")
//    }
//}

fun sum(a: Int, b: Int, continuation: Continuation<Int>) =
    continuation.resumeWith(a + b)

fun main() {
    with(EmptyContext) {
        sum(1, 2, continuation { resultX ->
            sum(2, 3, continuation { resultY ->
                sum(resultX, resultY, continuation { result ->
                    println(result)
                })
            })
        })
    }
}
