package ru.smirnov.intership.r.test.grammar.components

/**
 * Class for storing map call.
 *
 * Map expression should have integer return type
 */
data class MapCall(val expression: Expression) : Call {
    override fun <T> accept(visitor: ComponentVisitor<T>): T? {
        return visitor.visitMapCall(this)
    }

    override fun buildString(builder: StringBuilder) {
        builder.append("map{")
        expression.buildString(builder)
        builder.append("}")
    }
}