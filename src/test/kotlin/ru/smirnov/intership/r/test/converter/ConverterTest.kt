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
                        ElementExpression()
                    )
                )
            )
        )
        val expected = CallChain(
            listOf(
                FilterCall(
                    BinaryExpression(
                        ConstantExpression("1"),
                        ConstantExpression("1"),
                        Equality
                    )
                ),
                MapCall(
                    ElementExpression()
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
                        BinaryExpression(
                            ElementExpression(),
                            ConstantExpression("42"),
                            Greater
                        )
                    )
                )
            )
        )
        val expected = CallChain(
            listOf(
                FilterCall(
                    BinaryExpression(
                        ElementExpression(),
                        ConstantExpression("42"),
                        Greater
                    )
                ),
                MapCall(
                    ElementExpression()
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
        val converted = Converter().convert(expected)
        assertEquals(expected, converted)
    }

    @Test
    fun shouldConvertSample1() {
        val converted = Converter().convert(
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
        val expected = CallChain(
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
        assertEquals(expected, converted)
    }

    @Test
    fun shouldConvertSample2() {
        val converted = Converter().convert(
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
        val expected = CallChain(
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
        assertEquals(expected, converted)
    }

    @Test
    fun shouldConvertSample4() {
        val converted = Converter().convert(
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
        val expected = CallChain(
            listOf(
                FilterCall(
                    BinaryExpression(
                        BinaryExpression(
                            ElementExpression(),
                            ConstantExpression("0"),
                            Greater
                        ),
                        BinaryExpression(
                            ElementExpression(),
                            ConstantExpression("0"),
                            Less
                        ),
                        And
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
        assertEquals(expected, converted)
    }
}
