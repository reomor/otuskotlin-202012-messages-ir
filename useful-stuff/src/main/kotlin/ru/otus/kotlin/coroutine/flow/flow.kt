package ru.otus.kotlin.coroutine.flow

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun <T : Any> Flow<T>.zipWithNext(): Flow<Pair<T, T>> = flow {
    var current: T? = null
    collect { next ->
        if (current != null) emit(current!! to next)
        current = next
    }
}

fun main() = runBlocking<Unit> {

    flow<Int> { emit(1) }
        .onEach { i -> println(i) }
        .map { i -> i * 10 }
        .filter { i -> i % 3 != 0 }
        .collect { value -> println(value) }

    listOf(1, 2, 3)
        .asFlow()
        .zipWithNext()
        .collect { pair -> println(pair) }

    listOf(1)
        .asFlow()
        .zipWithNext()
        .collect { pair -> println(pair) }
}
