package ru.otus.kotlin

import org.junit.jupiter.api.Test
import kotlin.test.junit5.JUnit5Asserter.assertEquals

internal class NodeServiceTestJvm {

    @Test
    fun createNextNode() {
        val nodeModel: NodeModel = NodeModel(1, "description")
        val nextNode = createNextNode(nodeModel)
        assertEquals("", nodeModel.value + 1, nextNode.value)
    }
}