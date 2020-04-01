package ru.smirnov.intership.r.test.grammar.components

interface Call {
    fun <T> accept(visitor: ComponentVisitor<T>): T?

    fun buildString(builder: StringBuilder)
}