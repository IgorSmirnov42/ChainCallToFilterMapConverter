package ru.smirnov.intership.r.test.grammar.components

class FilterCall(val expression: Expression) : Call {
    override fun <T> accept(visitor: ComponentVisitor<T>): T? {
        return visitor.visitFilterCall(this)
    }

    override fun equals(other: Any?): Boolean {
        return other is FilterCall && other.expression == expression
    }

    override fun toString(): String {
        return "filter{$expression}"
    }
}