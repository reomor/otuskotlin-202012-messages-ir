package ru.otus.kotlin.coroutine

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {

    launch {
        println("async 31")
        delay(6_000)
        println("sync 32")
    }

    coroutineScope {
        launch {
            println("async 11")
            delay(2_000)
            println("sync 12")
        }
        launch {
            println("async 21")
            delay(4_000)
            println("sync 22")
        }
    }
}
