package ru.otus.kotlin

actual fun createNextNode(nodeModel: NodeModel): NodeModel = NodeModel(
    value = nodeModel.value + 1,
    description = nodeModel.description
)
