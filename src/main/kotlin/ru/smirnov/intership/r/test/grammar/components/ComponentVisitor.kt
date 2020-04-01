package ru.smirnov.intership.r.test.grammar.components

/**
 * Visitor for all components of a grammar.
 *
 * @param T -- each method will return result of `T?` type. By default, all methods return null
 */
interface ComponentVisitor<T> {
    fun visitBinaryExpression(expression: BinaryExpression): T? {
        expression.leftExpression.accept(this)
        expression.rightExpression.accept(this)
        return null
    }

    fun visitConstantExpression(expression: ConstantExpression): T? {
        return null
    }

    fun visitElementExpression(expression: ElementExpression): T? {
        return null
    }

    fun visitFilterCall(call: FilterCall): T? {
        call.expression.accept(this)
        return null
    }

    fun visitMapCall(call: MapCall): T? {
        call.expression.accept(this)
        return null
    }

    fun visitCallChain(callChain: CallChain): T? {
        for (call in callChain.calls) {
            call.accept(this)
        }
        return null
    }
}