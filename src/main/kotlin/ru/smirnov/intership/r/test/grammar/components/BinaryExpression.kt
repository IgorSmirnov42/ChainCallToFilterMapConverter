package ru.smirnov.intership.r.test.grammar.components

/**
 * Class for storing binary expression operation over two integers or over two booleans
 */
class BinaryExpression(val leftExpression: Expression,
                       val rightExpression: Expression,
                       val operation: Operation) : Expression {
    override fun <T> accept(visitor: ComponentVisitor<T>): T? {
        return visitor.visitBinaryExpression(this)
    }

    override fun buildString(builder: StringBuilder) {
        builder.append("(")
        leftExpression.buildString(builder)
        operation.buildString(builder)
        rightExpression.buildString(builder)
        builder.append(")")
    }

    override fun equals(other: Any?): Boolean {
        return other is BinaryExpression && other.leftExpression == this.leftExpression &&
                other.rightExpression == this.rightExpression && other.operation == this.operation
    }

    override fun toString(): String {
        return "($leftExpression$operation$rightExpression)"
    }
}