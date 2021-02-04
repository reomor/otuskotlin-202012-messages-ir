package ru.otus.kotlin.coroutine

import kotlinx.coroutines.*

//fun main() = runBlocking<Unit> {
//    supervisorScope {
//        launch {
//            println("11")
//            delay(3_000)
//            println("12")
//        }
//        launch {
//            println("21")
//            delay(1_000)
//            throw RuntimeException()
//        }
//    }
//}

fun main() = runBlocking<Unit> {
    val scope = CoroutineScope(SupervisorJob())
    coroutineScope {
        scope.launch {
            println("11")
            delay(3_000)
            println("12")
        }
        scope.launch {
            println("21")
            delay(1_000)
            throw RuntimeException()
        }
    }
}
