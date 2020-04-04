package ru.smirnov.intership.r.test.typecheck

import org.junit.Test
import ru.smirnov.intership.r.test.grammar.components.*

class TypeCheckerTest {
    @Test
    fun shouldApproveSimpleCorrectTree() {
        TypeChecker().checkTypes(
            CallChain(
                listOf(
                    MapCall(
                        ElementExpression
                    )
                )
            )
        )
    }

    @Test
    fun shouldApproveTreeWithBinaryException() {
        TypeChecker().checkTypes(
            CallChain(
                listOf(
                    MapCall(
                        Subtraction(
                            ElementExpression,
                            ConstantExpression("-42")
                        )
                    )
                )
            )
        )
    }

    @Test(expected = TypeErrorException::class)
    fun shouldThrowOnElementTypeInFilter() {
        TypeChecker().checkTypes(
            CallChain(
                listOf(
                    FilterCall(
                        ElementExpression
                    )
                )
            )
        )
    }

    @Test(expected = TypeErrorException::class)
    fun shouldThrowOnComplexIntegerExpressionInFilter() {
        TypeChecker().checkTypes(
            CallChain(
                listOf(
                    FilterCall(
                        And(
                            Greater(
                                ElementExpression,
                                ConstantExpression("42")
                            ),
                            Addition(
                                ConstantExpression("42"),
                                ConstantExpression("23")
                            )
                        )
                    )
                )
            )
        )
    }

    @Test
    fun shouldApproveComplexBooleanExpressionWithIntegerExpressionsInside() {
        TypeChecker().checkTypes(
            CallChain(
                listOf(
                    FilterCall(
                        And(
                            Greater(
                                ElementExpression,
                                ConstantExpression("42")
                            ),
                            Less(
                                Multiplication(
                                    ElementExpression,
                                    ConstantExpression("-42")
                                ),
                                ConstantExpression("23")
                            )
                        )
                    )
                )
            )
        )
    }

    @Test(expected = TypeErrorException::class)
    fun shouldThrowOnBooleanExpressionInMap() {
        TypeChecker().checkTypes(
            CallChain(
                listOf(
                    MapCall(
                        Equality(
                            ElementExpression,
                            ElementExpression
                        )
                    )
                )
            )
        )
    }

    @Test
    fun shouldApproveSample1() {
        TypeChecker().checkTypes(
            CallChain(
                listOf(
                    FilterCall(
                        Greater(
                            ElementExpression,
                            ConstantExpression("10")
                        )
                    ),
                    FilterCall(
                        Less(
                            ElementExpression,
                            ConstantExpression("20")
                        )
                    )
                )
            )
        )
    }

    @Test
    fun shouldApproveSample1Answer() {
        TypeChecker().checkTypes(
            CallChain(
                listOf(
                    FilterCall(
                        And(
                            Greater(
                                ElementExpression,
                                ConstantExpression("10")
                            ),
                            Less(
                                ElementExpression,
                                ConstantExpression("20")
                            )
                        )
                    ),
                    MapCall(
                        ElementExpression
                    )
                )
            )
        )
    }

    @Test
    fun shouldApproveSample2() {
        TypeChecker().checkTypes(
            CallChain(
                listOf(
                    MapCall(
                        Addition(
                            ElementExpression,
                            ConstantExpression("10")
                        )
                    ),
                    FilterCall(
                        Greater(
                            ElementExpression,
                            ConstantExpression("10")
                        )
                    ),
                    MapCall(
                        Multiplication(
                            ElementExpression,
                            ElementExpression
                        )
                    )
                )
            )
        )
    }

    @Test
    fun shouldApproveSample2Answer() {
        TypeChecker().checkTypes(
            CallChain(
                listOf(
                    FilterCall(
                        Greater(
                            Addition(
                                ElementExpression,
                                ConstantExpression("10")
                            ),
                            ConstantExpression("10")
                        )
                    ),
                    MapCall(
                        Multiplication(
                            Addition(
                                ElementExpression,
                                ConstantExpression("10")
                            ),
                            Addition(
                                ElementExpression,
                                ConstantExpression("10")
                            )
                        )
                    )
                )
            )
        )
    }

    @Test
    fun shouldApproveSample3Answer() {
        TypeChecker().checkTypes(
            CallChain(
                listOf(
                    FilterCall(
                        Greater(
                            ElementExpression,
                            ConstantExpression("0")
                        )
                    ),
                    MapCall(
                        Addition(
                            Multiplication(
                                ElementExpression,
                                ElementExpression
                            ),
                            Addition(
                                Multiplication(
                                    ElementExpression,
                                    ConstantExpression("20")
                                ),
                                ConstantExpression("100")
                            )
                        )
                    )
                )
            )
        )
    }

    @Test
    fun shouldApproveSample4() {
        TypeChecker().checkTypes(
            CallChain(
                listOf(
                    FilterCall(
                        Greater(
                            ElementExpression,
                            ConstantExpression("0")
                        )
                    ),
                    FilterCall(
                        Less(
                            ElementExpression,
                            ConstantExpression("0")
                        )
                    ),
                    MapCall(
                        Multiplication(
                            ElementExpression,
                            ElementExpression
                        )
                    )
                )
            )
        )
    }

    @Test
    fun shouldApproveSample4Answer() {
        TypeChecker().checkTypes(
            CallChain(
                listOf(
                    FilterCall(
                        Equality(
                            ConstantExpression("1"),
                            ConstantExpression("0")
                        )
                    ),
                    MapCall(
                        ElementExpression
                    )
                )
            )
        )
    }
}
