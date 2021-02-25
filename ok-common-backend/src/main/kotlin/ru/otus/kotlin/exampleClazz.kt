package ru.otus.kotlin

sealed class Node
object Empty : Node()
data class IntegerNode(val value: Int)
