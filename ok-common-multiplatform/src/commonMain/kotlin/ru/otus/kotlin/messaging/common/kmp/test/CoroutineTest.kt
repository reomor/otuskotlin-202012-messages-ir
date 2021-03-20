package ru.otus.kotlin.messaging.common.kmp.test

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

expect val testCoroutineContext: CoroutineContext

expect fun runBlockingTest(block: suspend CoroutineScope.()-> Unit)

//// JVM code:
//actual val testCoroutineContext: CoroutineContext =
//    Executors.newSingleThreadExecutor().asCoroutineDispatcher()
//actual fun runBlockingTest(block: suspend CoroutineScope.() -> Unit) =
//    runBlocking(testCoroutineContext) { this.block() }
//
//// JS code:
//val testScope = MainScope()
//actual val testCoroutineContext: CoroutineContext = testScope.coroutineContext
//actual fun runBlockingTest(block: suspend CoroutineScope.() -> Unit): dynamic = testScope.promise { this.block() }
//
////iOS code:
//actual val testCoroutineContext: CoroutineContext =
//    newSingleThreadContext("testRunner")
//
//actual fun runBlockingTest(block: suspend CoroutineScope.() -> Unit) =
//    runBlocking(testCoroutineContext) { this.block() }
