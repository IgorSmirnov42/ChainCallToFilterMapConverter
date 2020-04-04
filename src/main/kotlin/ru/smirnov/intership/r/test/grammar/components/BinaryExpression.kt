package ru.smirnov.intership.r.test.grammar.components

import java.lang.IllegalArgumentException

/**
 * Abstract class for storing binary expression operation over two integers or over two booleans
 */
sealed class BinaryExpression(val leftExpression: Expression,
                              val rightExpression: Expression) : Expression {

    companion object {
        fun create(operation: String, leftExpression: Expression, rightExpression: Expression): BinaryExpression {
            return when (operation) {
                "*" -> Multiplication(leftExpression, rightExpression)
                "+" -> Addition(leftExpression, rightExpression)
                "-" -> Subtraction(leftExpression, rightExpression)
                "=" -> Equality(leftExpression, rightExpression)
                "&" -> And(leftExpression, rightExpression)
                "|" -> Or(leftExpression, rightExpression)
                ">" -> Greater(leftExpression, rightExpression)
                "<" -> Less(leftExpression, rightExpression)
                else -> throw IllegalArgumentException("No such operation: $operation")
            }
        }
    }

    abstract fun operationString(): String

    override fun buildString(builder: StringBuilder) {
        builder.append("(")
        leftExpression.buildString(builder)
        builder.append(operationString())
        rightExpression.buildString(builder)
        builder.append(")")
    }

    override fun toString(): String {
        return "($leftExpression${operationString()}$rightExpression)"
    }
}

/**
 * Operands: Integers
 * Result: Integer
 */
class Multiplication(leftExpression: Expression, rightExpression: Expression) :
    BinaryExpression(leftExpression, rightExpression) {

    override fun operationString(): String = "*"
    override fun <T> accept(visitor: ComponentVisitor<T>): T? {
        return visitor.visitMultiplication(this)
    }

    override fun equals(other: Any?): Boolean {
        return other is Multiplication && other.leftExpression == this.leftExpression &&
                other.rightExpression == this.rightExpression
    }
}

/**
 * Operands: Integers
 * Result: Integer
 */
class Addition(leftExpression: Expression, rightExpression: Expression) :
    BinaryExpression(leftExpression, rightExpression) {

    override fun operationString(): String = "+"
    override fun <T> accept(visitor: ComponentVisitor<T>): T? {
        return visitor.visitAddition(this)
    }

    override fun equals(other: Any?): Boolean {
        return other is Addition && other.leftExpression == this.leftExpression &&
                other.rightExpression == this.rightExpression
    }
}

/**
 * Operands: Integers
 * Result: Integer
 */
class Subtraction(leftExpression: Expression, rightExpression: Expression) :
    BinaryExpression(leftExpression, rightExpression) {

    override fun operationString(): String = "-"
    override fun <T> accept(visitor: ComponentVisitor<T>): T? {
        return visitor.visitSubtraction(this)
    }

    override fun equals(other: Any?): Boolean {
        return other is Subtraction && other.leftExpression == this.leftExpression &&
                other.rightExpression == this.rightExpression
    }
}

/**
 * Operands: Integers
 * Result: Boolean
 */
class Equality(leftExpression: Expression, rightExpression: Expression) :
    BinaryExpression(leftExpression, rightExpression) {

    override fun operationString(): String = "="
    override fun <T> accept(visitor: ComponentVisitor<T>): T? {
        return visitor.visitEquality(this)
    }

    override fun equals(other: Any?): Boolean {
        return other is Equality && other.leftExpression == this.leftExpression &&
                other.rightExpression == this.rightExpression
    }
}

/**
 * Operands: Booleans
 * Result: Boolean
 */
class And(leftExpression: Expression, rightExpression: Expression) :
    BinaryExpression(leftExpression, rightExpression) {

    override fun operationString(): String = "&"
    override fun <T> accept(visitor: ComponentVisitor<T>): T? {
        return visitor.visitAnd(this)
    }

    override fun equals(other: Any?): Boolean {
        return other is And && other.leftExpression == this.leftExpression &&
                other.rightExpression == this.rightExpression
    }
}

/**
 * Operands: Booleans
 * Result: Boolean
 */
class Or(leftExpression: Expression, rightExpression: Expression) :
    BinaryExpression(leftExpression, rightExpression) {

    override fun operationString(): String = "|"
    override fun <T> accept(visitor: ComponentVisitor<T>): T? {
        return visitor.visitOr(this)
    }

    override fun equals(other: Any?): Boolean {
        return other is Or && other.leftExpression == this.leftExpression &&
                other.rightExpression == this.rightExpression
    }
}

/**
 * Operands: Integers
 * Result: Boolean
 */
class Greater(leftExpression: Expression, rightExpression: Expression) :
    BinaryExpression(leftExpression, rightExpression) {

    override fun operationString(): String = ">"
    override fun <T> accept(visitor: ComponentVisitor<T>): T? {
        return visitor.visitGreater(this)
    }

    override fun equals(other: Any?): Boolean {
        return other is Greater && other.leftExpression == this.leftExpression &&
                other.rightExpression == this.rightExpression
    }
}

/**
 * Operands: Integers
 * Result: Boolean
 */
class Less(leftExpression: Expression, rightExpression: Expression) :
    BinaryExpression(leftExpression, rightExpression) {

    override fun operationString(): String = "<"
    override fun <T> accept(visitor: ComponentVisitor<T>): T? {
        return visitor.visitLess(this)
    }

    override fun equals(other: Any?): Boolean {
        return other is Less && other.leftExpression == this.leftExpression &&
                other.rightExpression == this.rightExpression
    }
}
