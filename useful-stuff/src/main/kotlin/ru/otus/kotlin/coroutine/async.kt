package ru.otus.kotlin.coroutine

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {

    val globalAsync = GlobalScope.async {
        println("async 1")
        delay(6_000)
        println("sync 2")
        42
    }

    launch {
        println("async 1")
        delay(3_000)
        println("sync 2")
    }

    val async: Deferred<Int> = async {
        println("async 1")
        delay(3_000)
        println("sync 2")
        1
    }

    println(async.await())
    //println(globalAsync.await())
}
