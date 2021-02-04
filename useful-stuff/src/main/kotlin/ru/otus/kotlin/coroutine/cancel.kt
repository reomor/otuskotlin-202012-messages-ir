package ru.otus.kotlin.coroutine

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val job = launch {
        try {
            delay(10_000)
        } catch (e: CancellationException) {
            println("cancelled")
        }
    }
    delay(1_000)
    job.cancel()
}
