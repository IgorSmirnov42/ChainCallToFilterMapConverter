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
                        ElementExpression()
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
                        BinaryExpression(
                            ElementExpression(),
                            ConstantExpression("-42"),
                            Subtraction
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
                        ElementExpression()
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
                        BinaryExpression(
                            BinaryExpression(
                                ElementExpression(),
                                ConstantExpression("42"),
                                Greater
                            ),
                            BinaryExpression(
                                ConstantExpression("42"),
                                ConstantExpression("23"),
                                Addition
                            ),
                            And
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
                        BinaryExpression(
                            BinaryExpression(
                                ElementExpression(),
                                ConstantExpression("42"),
                                Greater
                            ),
                            BinaryExpression(
                                BinaryExpression(
                                    ElementExpression(),
                                    ConstantExpression("-42"),
                                    Multiplication
                                ),
                                ConstantExpression("23"),
                                Less
                            ),
                            And
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
                        BinaryExpression(
                            ElementExpression(),
                            ElementExpression(),
                            Equality
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
                        BinaryExpression(
                            ElementExpression(),
                            ConstantExpression("10"),
                            Greater
                        )
                    ),
                    FilterCall(
                        BinaryExpression(
                            ElementExpression(),
                            ConstantExpression("20"),
                            Less
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
                        BinaryExpression(
                            BinaryExpression(
                                ElementExpression(),
                                ConstantExpression("10"),
                                Greater
                            ),
                            BinaryExpression(
                                ElementExpression(),
                                ConstantExpression("20"),
                                Less
                            ),
                            And
                        )
                    ),
                    MapCall(
                        ElementExpression()
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
                        BinaryExpression(
                            ElementExpression(),
                            ConstantExpression("10"),
                            Addition
                        )
                    ),
                    FilterCall(
                        BinaryExpression(
                            ElementExpression(),
                            ConstantExpression("10"),
                            Greater
                        )
                    ),
                    MapCall(
                        BinaryExpression(
                            ElementExpression(),
                            ElementExpression(),
                            Multiplication
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
                        BinaryExpression(
                            BinaryExpression(
                                ElementExpression(),
                                ConstantExpression("10"),
                                Addition
                            ),
                            ConstantExpression("10"),
                            Greater
                        )
                    ),
                    MapCall(
                        BinaryExpression(
                            BinaryExpression(
                                ElementExpression(),
                                ConstantExpression("10"),
                                Addition
                            ),
                            BinaryExpression(
                                ElementExpression(),
                                ConstantExpression("10"),
                                Addition
                            ),
                            Multiplication
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
                        BinaryExpression(
                            ElementExpression(),
                            ConstantExpression("0"),
                            Greater
                        )
                    ),
                    MapCall(
                        BinaryExpression(
                            BinaryExpression(
                                ElementExpression(),
                                ElementExpression(),
                                Multiplication
                            ),
                            BinaryExpression(
                                BinaryExpression(
                                    ElementExpression(),
                                    ConstantExpression("20"),
                                    Multiplication
                                ),
                                ConstantExpression("100"),
                                Addition
                            ),
                            Addition
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
                        BinaryExpression(
                            ElementExpression(),
                            ConstantExpression("0"),
                            Greater
                        )
                    ),
                    FilterCall(
                        BinaryExpression(
                            ElementExpression(),
                            ConstantExpression("0"),
                            Less
                        )
                    ),
                    MapCall(
                        BinaryExpression(
                            ElementExpression(),
                            ElementExpression(),
                            Multiplication
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
                        BinaryExpression(
                            ConstantExpression("1"),
                            ConstantExpression("0"),
                            Equality
                        )
                    ),
                    MapCall(
                        ElementExpression()
                    )
                )
            )
        )
    }
}
