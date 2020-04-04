package ru.smirnov.intership.r.test.grammar.components

/**
 * Visitor for all components of a grammar.
 *
 * @param T -- each method will return result of `T?` type. By default, all methods return null
 */
interface ComponentVisitor<T> {

    fun visitMultiplication(expression: Multiplication): T? {
        return visitBinaryExpression(expression)
    }

    fun visitEquality(expression: Equality): T? {
        return visitBinaryExpression(expression)
    }

    fun visitLess(expression: Less): T? {
        return visitBinaryExpression(expression)
    }

    fun visitGreater(expression: Greater): T? {
        return visitBinaryExpression(expression)
    }

    fun visitAddition(expression: Addition): T? {
        return visitBinaryExpression(expression)
    }

    fun visitSubtraction(expression: Subtraction): T? {
        return visitBinaryExpression(expression)
    }

    fun visitOr(expression: Or): T? {
        return visitBinaryExpression(expression)
    }

    fun visitAnd(expression: And): T? {
        return visitBinaryExpression(expression)
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

    fun visitBinaryExpression(expression: BinaryExpression): T? {
        expression.leftExpression.accept(this)
        expression.rightExpression.accept(this)
        return null
    }
}