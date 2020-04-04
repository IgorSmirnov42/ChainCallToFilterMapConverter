package ru.smirnov.intership.r.test.parser.visitors

import ru.smirnov.intership.r.test.ArrayFilterMapBaseVisitor
import ru.smirnov.intership.r.test.ArrayFilterMapParser
import ru.smirnov.intership.r.test.grammar.components.*

/** Parses expressions */
class ExpressionVisitor : ArrayFilterMapBaseVisitor<Expression>() {
    override fun visitBinaryExpression(ctx: ArrayFilterMapParser.BinaryExpressionContext): Expression {
        return BinaryExpression.create(ctx.operation().text,
                                ctx.expression(0).accept(this),
                                ctx.expression(1).accept(this))
    }

    override fun visitConstantExpression(ctx: ArrayFilterMapParser.ConstantExpressionContext): Expression {
        return ConstantExpression(ctx.text)
    }

    override fun visitElement(ctx: ArrayFilterMapParser.ElementContext): Expression {
        return ElementExpression
    }
}