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
        if (!expression.operation.isInteger()) {
            throw TypeErrorException("Integer operation expected, boolean found")
        }
        expression.leftExpression.accept(IntegerExpressionTypeCheckVisitor())
        expression.rightExpression.accept(IntegerExpressionTypeCheckVisitor())
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
        if (!expression.operation.isBoolean()) {
            throw TypeErrorException("Boolean operation expected, integer found")
        }
        expression.leftExpression.accept(IntegerExpressionTypeCheckVisitor())
        expression.rightExpression.accept(IntegerExpressionTypeCheckVisitor())
    }
}
