package ru.smirnov.intership.r.test.parser

import org.junit.Assert.*
import org.junit.Test
import ru.smirnov.intership.r.test.grammar.components.*

class ParserTest {
    @Test
    fun shouldParseOneCall() {
        val expected = CallChain(
            listOf(
                FilterCall(ElementExpression)
            )
        )
        assertEquals(expected, Parser().parse("filter{element}"))
    }

    @Test
    fun shouldParseBinaryExpression() {
        val expected = CallChain(
            listOf(
                FilterCall (
                    Addition(ElementExpression, ElementExpression)
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
                    Multiplication(
                        Subtraction(
                            ElementExpression,
                            ConstantExpression("42")
                        ),
                        ConstantExpression("-42")
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
                    Multiplication(
                        Subtraction(
                            ElementExpression,
                            ConstantExpression("42")
                        ),
                        ConstantExpression("-42")
                    )
                ),
                MapCall(
                    Multiplication(
                        Subtraction(
                            ConstantExpression("-42"),
                            ConstantExpression("-42")
                        ),
                        ElementExpression
                    )
                ),
                MapCall(
                    ElementExpression
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
        assertEquals(expected, Parser().parse("filter{(element>10)}%>%filter{(element<20)}"))
    }

    @Test
    fun shouldParseSample1Answer() {
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
        assertEquals(expected, Parser().parse("filter{((element>10)&(element<20))}%>%map{element}"))
    }

    @Test
    fun shouldParseSample2() {
        val expected = CallChain(
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
        assertEquals(expected, Parser().parse(
            "map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}"
        ))
    }

    @Test
    fun shouldParseSample2Answer() {
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
        assertEquals(expected, Parser().parse(
            "filter{((element+10)>10)}%>%map{((element+10)*(element+10))}"
        ))
    }

    @Test
    fun shouldParseSample3Answer() {
        val expected = CallChain(
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
        assertEquals(expected, Parser().parse(
            "filter{(element>0)}%>%map{((element*element)+((element*20)+100))}"
        ))
    }

    @Test
    fun shouldParseSample4() {
        val expected = CallChain(
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
        assertEquals(expected, Parser().parse(
            "filter{(element>0)}%>%filter{(element<0)}%>%map{(element*element)}"
        ))
    }

    @Test
    fun shouldParseSample4Answer() {
        val expected = CallChain(
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
