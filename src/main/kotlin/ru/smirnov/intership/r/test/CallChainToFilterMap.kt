package ru.smirnov.intership.r.test

import ru.smirnov.intership.r.test.converter.Converter
import ru.smirnov.intership.r.test.parser.Parser
import ru.smirnov.intership.r.test.parser.ParserException
import ru.smirnov.intership.r.test.typecheck.TypeChecker
import ru.smirnov.intership.r.test.typecheck.TypeErrorException
import java.lang.Exception

object CallChainToFilterMap {
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