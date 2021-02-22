package ru.otus.kotlin

import org.junit.jupiter.api.Test
import java.security.SecureRandom
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ExampleTestClass {

    @Test
    fun `test 01`() {
        val node: IntegerNode = IntegerNode(1)
        assertEquals(node.value, 1)
    }
}
