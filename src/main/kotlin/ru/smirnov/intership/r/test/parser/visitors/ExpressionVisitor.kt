package ru.smirnov.intership.r.test.parser.visitors

import ru.smirnov.intership.r.test.ArrayFilterMapBaseVisitor
import ru.smirnov.intership.r.test.ArrayFilterMapParser
import ru.smirnov.intership.r.test.grammar.components.*

class ExpressionVisitor : ArrayFilterMapBaseVisitor<Expression>() {
    override fun visitBinaryExpression(ctx: ArrayFilterMapParser.BinaryExpressionContext): Expression {
        return BinaryExpression(ctx.expression(0).accept(this),
                                ctx.expression(1).accept(this),
                                Operation.fromString(ctx.operation().text))
    }

    override fun visitConstantExpression(ctx: ArrayFilterMapParser.ConstantExpressionContext): Expression {
        return ConstantExpression(ctx.text)
    }

    override fun visitElement(ctx: ArrayFilterMapParser.ElementContext): Expression {
        return ElementExpression()
    }
}