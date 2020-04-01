package ru.smirnov.intership.r.test.converter

import ru.smirnov.intership.r.test.grammar.components.CallChain

class Converter {
    fun convert(callChain: CallChain): CallChain {
        val conversionVisitor = FilterMapConversionVisitor()
        return callChain.accept(conversionVisitor)!!
    }
}
