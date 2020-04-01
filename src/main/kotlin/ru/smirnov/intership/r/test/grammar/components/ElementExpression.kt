package ru.smirnov.intership.r.test.grammar.components

class ElementExpression : Expression {
    override fun <T> accept(visitor: ComponentVisitor<T>): T? {
        return visitor.visitElementExpression(this)
    }

    override fun buildString(builder: StringBuilder) {
        builder.append("element")
    }

    override fun equals(other: Any?): Boolean {
        return other is ElementExpression
    }
}