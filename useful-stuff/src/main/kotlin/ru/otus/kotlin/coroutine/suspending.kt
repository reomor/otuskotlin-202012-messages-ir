package ru.otus.kotlin.coroutine

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

suspend fun firstCalculation(): Int {
    delay(1_000)
    return 42 - 13
}

suspend fun secondCalculation(): Int {
    delay(1_000)
    return 42 - (42 - 13)
}

fun firstCalculationAsync() = GlobalScope.async {
    firstCalculation()
}

fun secondCalculationAsync() = GlobalScope.async {
    secondCalculation()
}

suspend fun concurrentSum() = coroutineScope {
    val first = async { firstCalculation() }
    val second = async { secondCalculation() }
    first.await() + second.await()
}

suspend fun failedConcurrentSum() = coroutineScope {
    val first = async<Int> {
        try {
            delay(Long.MAX_VALUE)
            42
        } finally {
            println("First child was cancelled")
        }
    }
    val second = async<Int> {
        println("Rise exception in child")
        throw RuntimeException()
    }
    first.await() + second.await()
}

fun main() = runBlocking<Unit> {

    val calculationTime = measureTimeMillis {
        val a = firstCalculation()
        val b = secondCalculation()
        println("result is ${a + b}")
    }
    println("calculation time is: $calculationTime")

    val asyncCalculationTime = measureTimeMillis {
        val asyncA = async { firstCalculation() }
        val asyncB = async { secondCalculation() }
        println("result is ${asyncA.await() + asyncB.await()}")
    }
    println("calculation time is: $asyncCalculationTime")

    val lazyCalculationTime = measureTimeMillis {
        val lazyA = async(start = CoroutineStart.LAZY) { firstCalculation() }
        val lazyB = async(start = CoroutineStart.LAZY) { secondCalculation() }
        lazyA.start()
        lazyB.start()
        println("result is ${lazyA.await() + lazyB.await()}")
    }
    println("calculation time is: $lazyCalculationTime")

    val globalAsyncTime = measureTimeMillis {
        val firstAsync = firstCalculationAsync()
        val secondAsync = secondCalculationAsync()

        println("result is ${firstAsync.await() + secondAsync.await()}")
    }
    println("calculation time is: $globalAsyncTime")

    try {
        failedConcurrentSum()
    } catch (e: RuntimeException) {
        println("Exception in child")
    }
}
