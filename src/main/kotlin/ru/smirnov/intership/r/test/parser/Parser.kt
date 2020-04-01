package ru.smirnov.intership.r.test.parser

import ru.smirnov.intership.r.test.grammar.components.CallChain
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import ru.smirnov.intership.r.test.ArrayFilterMapLexer
import ru.smirnov.intership.r.test.ArrayFilterMapParser
import ru.smirnov.intership.r.test.parser.visitors.CallChainVisitor

class Parser {
    fun parse(input: String): CallChain {
        try {
            val lexer = ArrayFilterMapLexer(CharStreams.fromString(input))
            val parser = ArrayFilterMapParser(CommonTokenStream(lexer))
            return parser.callChain().accept(CallChainVisitor())
        } catch (exception: RuntimeException) {
            throw ParserException(exception.message ?: "")
        }
    }
}