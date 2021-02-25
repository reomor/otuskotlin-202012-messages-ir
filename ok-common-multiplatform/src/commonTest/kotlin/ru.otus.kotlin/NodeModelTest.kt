package ru.otus.kotlin

import kotlin.test.Test
import kotlin.test.assertEquals

internal class NodeModelTest {

    @Test
    fun `create default nodeModel`() {
        val defaultNode = NodeModel()
        assertEquals(0, defaultNode.value)
        assertEquals("", defaultNode.description)
    }

    @Test
    fun `create nodeModel`() {
        val defaultNode = NodeModel(666, "Ave")
        assertEquals(666, defaultNode.value)
        assertEquals("Ave", defaultNode.description)
    }
}
