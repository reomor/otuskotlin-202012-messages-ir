import ru.otus.kotlin.messaging.common.kmp.test.runBlockingTest
import ru.otus.kotlin.messaging.pipeline.operation
import ru.otus.kotlin.messaging.pipeline.pipeline
import kotlin.test.Test
import kotlin.test.assertEquals

internal class OperationTest {

    data class TestContext(var str: String = "")

    @Test
    fun operationTest() {

        val context = TestContext("ab")

        val operation = operation<TestContext> {
            execute {
                str += "c"
            }
        }

        runBlockingTest {
            operation.execute(context)
            assertEquals("abc", context.str)
        }
    }

    @Test
    fun simplePipelineTest() {

        val context = TestContext("a")

        val bOperation = operation<TestContext> {
            execute {
                str += "b"
            }
        }

        val pipeline = pipeline<TestContext> {
            execute(bOperation)
            operation {
                execute {
                    str += "c"
                }
            }
            operation {
                startIf { false }
                execute {
                    str += "c"
                }
            }
        }

        runBlockingTest {
            pipeline.execute(context)
            assertEquals("abc", context.str)
        }
    }

    @Test
    fun pipelineTest() {

        val context = TestContext("a")

        val bOperation = operation<TestContext> {
            execute {
                str += "b"
            }
        }

        val pipeline = pipeline<TestContext> {
            execute(bOperation)
            execute { str += "c" }
            operation {
                startIf { false }
                execute {
                    str += "c"
                }
            }
        }

        runBlockingTest {
            pipeline.execute(context)
            assertEquals("abc", context.str)
        }
    }

    @Test
    fun nestedPipelineTest() {

        val context = TestContext("a")

        val bOperation = operation<TestContext> {
            execute {
                str += "b"
            }
        }

        val pipeline = pipeline<TestContext> {
            execute(bOperation)
            execute { str += "c" }
            operation {
                startIf { false }
                execute {
                    str += "c"
                }
            }
            pipeline {
                execute(bOperation)
                operation {
                    startIf { false }
                    execute {
                        str = ""
                    }
                }
                pipeline {
                    operation {
                        execute {
                            str += "a"
                        }
                    }
                }
            }
        }

        runBlockingTest {
            pipeline.execute(context)
            assertEquals("abcba", context.str)
        }
    }

    @Test
    fun pipelineExceptionTest() {

        val context = TestContext("")

        val exOperation = operation<TestContext> {
            execute {
                throw IllegalArgumentException("Unexpected exception")
            }
            onError { throwable -> println(throwable) }
        }

        val pipeline = pipeline<TestContext> {
            execute(exOperation)
        }

        runBlockingTest {
            pipeline.execute(context)
            assertEquals("", context.str)
        }
    }
}
