import ru.otus.kotlin.messaging.common.kmp.test.runBlockingTest
import ru.otus.kotlin.messaging.pipeline.Operation
import kotlin.test.Test
import kotlin.test.assertEquals

internal class OperationTest {

    data class TestContext(var str: String = "")

    @Test
    fun contextTest() {

        val context = TestContext("ab")

        val operation = Operation.Builder<TestContext>().apply {
            execute {
                str += "c"
            }
        }.build()

        runBlockingTest {
            operation.execute(context)
            assertEquals("abc", context.str)
        }
    }
}
