package ru.smirnov.intership.r.test.grammar.components

import java.lang.IllegalArgumentException

sealed class Operation {
    abstract fun isInteger(): Boolean
    fun isBoolean(): Boolean = !isInteger()
    abstract override fun toString(): String

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

object Multiplication : Operation() {
    override fun isInteger(): Boolean = true
    override fun toString(): String = "*"
}

object Addition : Operation() {
    override fun isInteger(): Boolean = true
    override fun toString(): String = "+"
}

object Subtraction : Operation() {
    override fun isInteger(): Boolean = true
    override fun toString(): String = "-"
}

object Equality : Operation() {
    override fun isInteger(): Boolean = false
    override fun toString(): String = "="
}

object And : Operation() {
    override fun isInteger(): Boolean = false
    override fun toString(): String = "&"
}

object Or : Operation() {
    override fun isInteger(): Boolean = false
    override fun toString(): String = "|"
}

object Greater : Operation() {
    override fun isInteger(): Boolean = false
    override fun toString(): String = ">"
}

object Less : Operation() {
    override fun isInteger(): Boolean = false
    override fun toString(): String = "<"
}
