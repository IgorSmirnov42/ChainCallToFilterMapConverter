package ru.smirnov.intership.r.test.grammar.components

/**
 * Class for storing filter call.
 *
 * Filter expression should have boolean return type
 */
data class FilterCall(val expression: Expression) : Call {
    override fun <T> accept(visitor: ComponentVisitor<T>): T? {
        return visitor.visitFilterCall(this)
    }

    override fun buildString(builder: StringBuilder) {
        builder.append("filter{")
        expression.buildString(builder)
        builder.append("}")
    }
}