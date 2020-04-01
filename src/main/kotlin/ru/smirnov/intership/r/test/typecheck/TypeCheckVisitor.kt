package ru.smirnov.intership.r.test.typecheck

import ru.smirnov.intership.r.test.grammar.components.*

class TypeCheckVisitor : ComponentVisitor<Unit> {
    override fun visitFilterCall(call: FilterCall) {
        call.expression.accept(BooleanExpressionTypeCheckVisitor())
    }

    override fun visitMapCall(call: MapCall) {
        call.expression.accept(IntegerExpressionTypeCheckVisitor())
    }
}

private class IntegerExpressionTypeCheckVisitor : ComponentVisitor<Unit> {
    override fun visitBinaryExpression(expression: BinaryExpression) {
        val operandsVisitor = when (expression.operation) {
            Addition, Multiplication, Subtraction -> IntegerExpressionTypeCheckVisitor()
            else -> throw TypeErrorException("Integer operation expected, ${expression.operation} found")
        }
        expression.leftExpression.accept(operandsVisitor)
        expression.rightExpression.accept(operandsVisitor)
    }
}

private class BooleanExpressionTypeCheckVisitor : ComponentVisitor<Unit> {
    override fun visitElementExpression(expression: ElementExpression) {
        throw TypeErrorException("Boolean expression expected, element found")
    }

    override fun visitConstantExpression(expression: ConstantExpression) {
        throw TypeErrorException("Boolean expression expected, constant found")
    }

    override fun visitBinaryExpression(expression: BinaryExpression) {
        val operandsVisitor = when (expression.operation) {
            And, Or -> BooleanExpressionTypeCheckVisitor()
            Less, Greater, Equality -> IntegerExpressionTypeCheckVisitor()
            else -> throw TypeErrorException("Boolean operation expected, ${expression.operation} found")
        }
        expression.leftExpression.accept(operandsVisitor)
        expression.rightExpression.accept(operandsVisitor)
    }
}
