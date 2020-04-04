package ru.smirnov.intership.r.test.typecheck

import ru.smirnov.intership.r.test.grammar.components.*

/**
 * Type checking first visitor.
 *
 * Delegates cheking to boolean and integer checkers
 */
class TypeCheckVisitor : ComponentVisitor<Unit> {
    override fun visitFilterCall(call: FilterCall) {
        call.expression.accept(BooleanExpressionTypeCheckVisitor())
    }

    override fun visitMapCall(call: MapCall) {
        call.expression.accept(IntegerExpressionTypeCheckVisitor())
    }
}

/** Checks expression that should have integer return type */
private class IntegerExpressionTypeCheckVisitor : ComponentVisitor<Unit> {
    override fun visitAddition(expression: Addition) {
        visitChildren(expression, IntegerExpressionTypeCheckVisitor())
    }

    override fun visitMultiplication(expression: Multiplication){
        visitChildren(expression, IntegerExpressionTypeCheckVisitor())
    }

    override fun visitSubtraction(expression: Subtraction) {
        visitChildren(expression, IntegerExpressionTypeCheckVisitor())
    }

    private fun visitChildren(expression: BinaryExpression, operandsVisitor: ComponentVisitor<Unit>) {
        expression.leftExpression.accept(operandsVisitor)
        expression.rightExpression.accept(operandsVisitor)
    }

    override fun visitBinaryExpression(expression: BinaryExpression) {
        throw TypeErrorException("Integer operation expected, ${expression.operationString()} found")
    }
}

/** Checks expression that should have boolean return type */
private class BooleanExpressionTypeCheckVisitor : ComponentVisitor<Unit> {
    override fun visitElementExpression(expression: ElementExpression) {
        throw TypeErrorException("Boolean expression expected, element found")
    }

    override fun visitConstantExpression(expression: ConstantExpression) {
        throw TypeErrorException("Boolean expression expected, constant found")
    }

    override fun visitAnd(expression: And)  {
        visitChildren(expression, BooleanExpressionTypeCheckVisitor())
    }

    override fun visitOr(expression: Or) {
        visitChildren(expression, BooleanExpressionTypeCheckVisitor())
    }

    override fun visitLess(expression: Less) {
        visitChildren(expression, IntegerExpressionTypeCheckVisitor())
    }

    override fun visitGreater(expression: Greater) {
        visitChildren(expression, IntegerExpressionTypeCheckVisitor())
    }

    override fun visitEquality(expression: Equality) {
        visitChildren(expression, IntegerExpressionTypeCheckVisitor())
    }

    private fun visitChildren(expression: BinaryExpression, operandsVisitor: ComponentVisitor<Unit>) {
        expression.leftExpression.accept(operandsVisitor)
        expression.rightExpression.accept(operandsVisitor)
    }

    override fun visitBinaryExpression(expression: BinaryExpression) {
        throw TypeErrorException("Boolean operation expected, ${expression.operationString()} found")
    }
}
