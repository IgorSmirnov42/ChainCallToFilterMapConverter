package ru.smirnov.intership.r.test.converter

import ru.smirnov.intership.r.test.grammar.components.CallChain

/**
 * Class for converting call chain to <filter-call> <map-call> form
 */
class Converter {
    /** Performs conversion from any correct call chain to <filter-call> <map-call> form */
    fun convert(callChain: CallChain): CallChain {
        val conversionVisitor = FilterMapConversionVisitor()
        return callChain.accept(conversionVisitor)!!
    }
}
