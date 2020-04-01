package ru.smirnov.intership.r.test.parser.visitors

import ru.smirnov.intership.r.test.ArrayFilterMapBaseVisitor
import ru.smirnov.intership.r.test.ArrayFilterMapParser
import ru.smirnov.intership.r.test.grammar.components.CallChain
import ru.smirnov.intership.r.test.grammar.components.CallChainBuilder

/** Parses call chain */
class CallChainVisitor : ArrayFilterMapBaseVisitor<CallChain>() {
    override fun visitCallChain(ctx: ArrayFilterMapParser.CallChainContext): CallChain {
        val callChainBuilder = CallChainBuilder()
        for (call in ctx.calls) {
            callChainBuilder.add(call.accept(CallVisitor()))
        }
        return callChainBuilder.build()
    }
}