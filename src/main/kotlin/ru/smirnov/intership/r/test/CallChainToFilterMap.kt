package ru.smirnov.intership.r.test

import ru.smirnov.intership.r.test.converter.Converter
import ru.smirnov.intership.r.test.parser.Parser
import ru.smirnov.intership.r.test.parser.ParserException
import ru.smirnov.intership.r.test.typecheck.TypeChecker
import ru.smirnov.intership.r.test.typecheck.TypeErrorException

/**
 * Class for conversion from chain call in any form to <filter-call> <map-call> form
 */
object CallChainToFilterMap {
    /**
     * Performs converion.
     *
     * @throws ParserException if `input` has syntax errors
     * @throws TypeErrorException if `input` has type errors
     */
    fun doConversion(input: String): String {
        val chainCall = Parser().parse(input)
        TypeChecker().checkTypes(chainCall)
        return Converter().convert(chainCall).toString()
    }
}

fun main() {
    try {
        println(CallChainToFilterMap.doConversion(readLine() ?: ""))
    } catch (parserException: ParserException) {
        println("SYNTAX ERROR")
    } catch (typeErrorException: TypeErrorException) {
        println("TYPE ERROR")
    }
}