package ru.smirnov.intership.r.test.parser.visitors

import ru.smirnov.intership.r.test.ArrayFilterMapBaseVisitor
import ru.smirnov.intership.r.test.ArrayFilterMapParser
import ru.smirnov.intership.r.test.grammar.components.Call
import ru.smirnov.intership.r.test.grammar.components.FilterCall
import ru.smirnov.intership.r.test.grammar.components.MapCall

/** Parses call */
class CallVisitor : ArrayFilterMapBaseVisitor<Call>() {
    override fun visitMapCall(ctx: ArrayFilterMapParser.MapCallContext): Call {
        return MapCall(ctx.expression().accept(ExpressionVisitor()))
    }

    override fun visitFilterCall(ctx: ArrayFilterMapParser.FilterCallContext): Call {
        return FilterCall(ctx.expression().accept(ExpressionVisitor()))
    }
}