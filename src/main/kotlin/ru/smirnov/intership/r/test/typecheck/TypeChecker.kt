package ru.smirnov.intership.r.test.typecheck

import ru.smirnov.intership.r.test.grammar.components.CallChain

class TypeChecker {
    fun checkTypes(callChain: CallChain) {
        callChain.accept(TypeCheckVisitor())
    }
}
