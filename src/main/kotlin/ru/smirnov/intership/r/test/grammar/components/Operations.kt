package ru.smirnov.intership.r.test.grammar.components

import java.lang.IllegalArgumentException
import java.lang.StringBuilder

/**
 * Binary operations.
 */
sealed class Operation {
    fun buildString(builder: StringBuilder) {
        builder.append(toString())
    }

    companion object {
        fun fromString(operation: String): Operation {
            return when (operation) {
                "*" -> Multiplication
                "+" -> Addition
                "-" -> Subtraction
                "=" -> Equality
                "&" -> And
                "|" -> Or
                ">" -> Greater
                "<" -> Less
                else -> throw IllegalArgumentException("No such operation: $operation")
            }
        }
    }
}

/**
 * Operands: Integers
 * Result: Integer
 */
object Multiplication : Operation() {
    override fun toString(): String = "*"
}

/**
 * Operands: Integers
 * Result: Integer
 */
object Addition : Operation() {
    override fun toString(): String = "+"
}

/**
 * Operands: Integers
 * Result: Integer
 */
object Subtraction : Operation() {
    override fun toString(): String = "-"
}

/**
 * Operands: Integers
 * Result: Boolean
 */
object Equality : Operation() {
    override fun toString(): String = "="
}

/**
 * Operands: Booleans
 * Result: Boolean
 */
object And : Operation() {
    override fun toString(): String = "&"
}

/**
 * Operands: Booleans
 * Result: Boolean
 */
object Or : Operation() {
    override fun toString(): String = "|"
}

/**
 * Operands: Integers
 * Result: Boolean
 */
object Greater : Operation() {
    override fun toString(): String = ">"
}

/**
 * Operands: Integers
 * Result: Boolean
 */
object Less : Operation() {
    override fun toString(): String = "<"
}
