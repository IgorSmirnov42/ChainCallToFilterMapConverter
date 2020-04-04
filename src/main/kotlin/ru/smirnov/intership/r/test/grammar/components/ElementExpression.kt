package ru.smirnov.intership.r.test.grammar.components

/**
 * Node for `element` in a tree.
 *
 * Element -- is current array's element in call chain
 */
object ElementExpression : Expression {
    override fun <T> accept(visitor: ComponentVisitor<T>): T? {
        return visitor.visitElementExpression(this)
    }

    override fun buildString(builder: StringBuilder) {
        builder.append("element")
    }
}
