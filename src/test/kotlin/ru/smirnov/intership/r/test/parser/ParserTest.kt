package ru.smirnov.intership.r.test.parser

import org.junit.Assert.*
import org.junit.Test
import ru.smirnov.intership.r.test.grammar.components.*

class ParserTest {
    @Test
    fun shouldParseOneCall() {
        val expected = CallChain(
            listOf(
                FilterCall(ElementExpression())
            )
        )
        assertEquals(expected, Parser().parse("filter{element}"))
    }

    @Test
    fun shouldParseBinaryExpression() {
        val expected = CallChain(
            listOf(
                FilterCall (
                    BinaryExpression(ElementExpression(), ElementExpression(), Addition)
                )
            )
        )
        assertEquals(expected, Parser().parse("filter{(element+element)}"))
    }

    @Test
    fun shouldParseConstantExpression() {
        val expected = CallChain(
            listOf(
                FilterCall (
                    ConstantExpression("-1")
                )
            )
        )
        assertEquals(expected, Parser().parse("filter{-1}"))
    }

    @Test
    fun shouldParseCombinedExpression() {
        val expected = CallChain(
            listOf(
                FilterCall (
                    BinaryExpression(
                        BinaryExpression(
                            ElementExpression(),
                            ConstantExpression("42"),
                            Subtraction
                        ),
                        ConstantExpression("-42"),
                        Multiplication
                    )
                )
            )
        )
        assertEquals(expected, Parser().parse("filter{((element-42)*-42)}"))
    }

    @Test
    fun shouldParseChainCall() {
        val expected = CallChain(
            listOf(
                FilterCall (
                    BinaryExpression(
                        BinaryExpression(
                            ElementExpression(),
                            ConstantExpression("42"),
                            Subtraction
                        ),
                        ConstantExpression("-42"),
                        Multiplication
                    )
                ),
                MapCall(
                    BinaryExpression(
                        BinaryExpression(
                            ConstantExpression("-42"),
                            ConstantExpression("-42"),
                            Subtraction
                        ),
                        ElementExpression(),
                        Multiplication
                    )
                ),
                MapCall(
                    ElementExpression()
                )
            )
        )
        assertEquals(expected, Parser().parse(
            "filter{((element-42)*-42)}%>%map{((-42--42)*element)}%>%map{element}"
        ))
    }

    @Test
    fun shouldParseSample1() {
        val expected = CallChain(
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
        assertEquals(expected, Parser().parse("filter{(element>10)}%>%filter{(element<20)}"))
    }

    @Test
    fun shouldParseSample1Answer() {
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
        assertEquals(expected, Parser().parse("filter{((element>10)&(element<20))}%>%map{element}"))
    }

    @Test
    fun shouldParseSample2() {
        val expected = CallChain(
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
        assertEquals(expected, Parser().parse(
            "map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}"
        ))
    }

    @Test
    fun shouldParseSample2Answer() {
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
        assertEquals(expected, Parser().parse(
            "filter{((element+10)>10)}%>%map{((element+10)*(element+10))}"
        ))
    }

    @Test
    fun shouldParseSample3Answer() {
        val expected = CallChain(
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
        assertEquals(expected, Parser().parse(
            "filter{(element>0)}%>%map{((element*element)+((element*20)+100))}"
        ))
    }

    @Test
    fun shouldParseSample4() {
        val expected = CallChain(
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
        assertEquals(expected, Parser().parse(
            "filter{(element>0)}%>%filter{(element<0)}%>%map{(element*element)}"
        ))
    }

    @Test
    fun shouldParseSample4Answer() {
        val expected = CallChain(
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
        assertEquals(expected, Parser().parse("filter{(1=0)}%>%map{element}"))
    }

    @Test(expected = ParserException::class)
    fun shouldThrowOnIncorrectInput() {
        Parser().parse("42")
    }

    @Test(expected = ParserException::class)
    fun shouldThrowIfSomeSuffixWasNotParsed() {
        Parser().parse("filter{(1=0)}%>%map{element}42")
    }

    @Test(expected = ParserException::class)
    fun shouldThrowOnSpaces() {
        Parser().parse("filter{(1 =0)}%>%map{element}")
    }

    @Test(expected = ParserException::class)
    fun shouldThrowIfBracketsInBinaryExpressionAreMissed() {
        Parser().parse("filter{1=0}%>%map{element}")
    }

    @Test(expected = ParserException::class)
    fun shouldThrowIfExtraBracketsAreInserted() {
        Parser().parse("filter{(1=0)}%>%map{(element)}")
    }

    @Test(expected = ParserException::class)
    fun shouldThrowOnEmptyInput() {
        Parser().parse("")
    }
}
