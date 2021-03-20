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
    fun pipelineTest() {

        val context = TestContext("a")

        val aOperation = operation<TestContext> {
            execute {
                str += "b"
            }
        }

        val pipeline = pipeline<TestContext> {
            execute(aOperation)
            execute(operation {
                execute {
                    str += "c"
                }
            })
            execute(operation {
                startIf { false }
                execute {
                    str += "c"
                }
            })
        }

        runBlockingTest {
            pipeline.execute(context)
            assertEquals("abc", context.str)
        }
    }
}
