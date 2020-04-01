package ru.smirnov.intership.r.test.grammar.components

/**
 * Class for storing filter call.
 *
 * Filter expression should have boolean return type
 */
class FilterCall(val expression: Expression) : Call {
    override fun <T> accept(visitor: ComponentVisitor<T>): T? {
        return visitor.visitFilterCall(this)
    }

    override fun buildString(builder: StringBuilder) {
        builder.append("filter{")
        expression.buildString(builder)
        builder.append("}")
    }

    override fun equals(other: Any?): Boolean {
        return other is FilterCall && other.expression == expression
    }
}