package ru.smirnov.intership.r.test.grammar.components

/**
 * Interface for call commands.
 *
 * This components are top-level components in call chain
 */
interface Call {
    fun <T> accept(visitor: ComponentVisitor<T>): T?

    /** Adds call name, body and close bracket for call */
    fun buildString(builder: StringBuilder)
}