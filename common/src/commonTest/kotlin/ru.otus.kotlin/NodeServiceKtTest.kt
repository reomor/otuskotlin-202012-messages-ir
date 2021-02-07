package ru.otus.kotlin

import kotlin.test.Test
import kotlin.test.assertEquals

internal class NodeServiceKtTest {

    @Test
    fun createNextNode() {
        val nodeModel: NodeModel = NodeModel(1, "description")
        val nextNode = createNextNode(nodeModel)
        assertEquals(nodeModel.value + 1, nextNode.value)
    }
}
