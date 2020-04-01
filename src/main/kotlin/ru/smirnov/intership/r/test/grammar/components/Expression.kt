package ru.smirnov.intership.r.test.grammar.components

interface Expression {
    fun <T> accept(visitor: ComponentVisitor<T>): T?

    fun buildString(builder: StringBuilder)
}