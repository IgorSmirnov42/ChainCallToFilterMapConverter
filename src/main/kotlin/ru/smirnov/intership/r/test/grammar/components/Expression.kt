package ru.smirnov.intership.r.test.grammar.components

/**
 * Interface for expressions.
 *
 * Expression may be integer or boolean
 */
interface Expression {
    fun <T> accept(visitor: ComponentVisitor<T>): T?

    fun buildString(builder: StringBuilder)
}