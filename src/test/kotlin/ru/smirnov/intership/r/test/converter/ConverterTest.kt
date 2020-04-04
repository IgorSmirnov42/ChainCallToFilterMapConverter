package ru.smirnov.intership.r.test.converter

import org.junit.Assert.*
import org.junit.Test
import ru.smirnov.intership.r.test.grammar.components.*

class ConverterTest {
    @Test
    fun shouldConvertOnlyMapChain() {
        val converted = Converter().convert(
            CallChain(
                listOf(
                    MapCall(
                        ElementExpression
                    )
                )
            )
        )
        val expected = CallChain(
            listOf(
                FilterCall(
                    Equality(
                        ConstantExpression("1"),
                        ConstantExpression("1")
                    )
                ),
                MapCall(
                    ElementExpression
                )
            )
        )
        assertEquals(expected, converted)
    }

    @Test
    fun shouldConvertOnlyFilterChain() {
        val converted = Converter().convert(
            CallChain(
                listOf(
                    FilterCall(
                        Greater(
                            ElementExpression,
                            ConstantExpression("42")
                        )
                    )
                )
            )
        )
        val expected = CallChain(
            listOf(
                FilterCall(
                    Greater(
                        ElementExpression,
                        ConstantExpression("42")
                    )
                ),
                MapCall(
                    ElementExpression
                )
            )
        )
        assertEquals(expected, converted)
    }

    @Test
    fun shouldNotChangeFilterMapChain() {
        val expected = CallChain(
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
        val converted = Converter().convert(expected)
        assertEquals(expected, converted)
    }

    @Test
    fun shouldConvertSample1() {
        val converted = Converter().convert(
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
        val expected = CallChain(
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
        assertEquals(expected, converted)
    }

    @Test
    fun shouldConvertSample2() {
        val converted = Converter().convert(
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
        val expected = CallChain(
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
        assertEquals(expected, converted)
    }

    @Test
    fun shouldConvertSample4() {
        val converted = Converter().convert(
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
        val expected = CallChain(
            listOf(
                FilterCall(
                    And(
                        Greater(
                            ElementExpression,
                            ConstantExpression("0")
                        ),
                        Less(
                            ElementExpression,
                            ConstantExpression("0")
                        )
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
        assertEquals(expected, converted)
    }
}
