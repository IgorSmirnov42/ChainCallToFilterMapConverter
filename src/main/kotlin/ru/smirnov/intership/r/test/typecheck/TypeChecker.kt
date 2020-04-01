package ru.smirnov.intership.r.test.typecheck

import ru.smirnov.intership.r.test.grammar.components.CallChain

/**
 * Class for checking types of expressions in chain call.
 *
 * I.e. filter expression should be boolean type and multiplication expression should take integers as operands
 */
class TypeChecker {
    /**
     * Checks types.
     *
     * @throws TypeErrorException if there are problems with types
     */
    fun checkTypes(callChain: CallChain) {
        callChain.accept(TypeCheckVisitor())
    }
}
