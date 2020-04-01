package ru.smirnov.intership.r.test

import org.junit.Assert.*
import org.junit.Test
import ru.smirnov.intership.r.test.parser.ParserException
import ru.smirnov.intership.r.test.typecheck.TypeErrorException

class CallChainToFilterMapTest {
    @Test
    fun shouldWorkSample1() {
        assertEquals(
            "filter{((element>10)&(element<20))}%>%map{element}",
            CallChainToFilterMap.doConversion("filter{(element>10)}%>%filter{(element<20)}")
        )
    }

    @Test
    fun shouldWorkSample2() {
        assertEquals(
            "filter{((element+10)>10)}%>%map{((element+10)*(element+10))}",
            CallChainToFilterMap.doConversion("map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}")
        )
    }

    @Test
    fun shouldWorkSample4() {
        assertEquals(
            "filter{((element>0)&(element<0))}%>%map{(element*element)}",
            CallChainToFilterMap.doConversion("filter{(element>0)}%>%filter{(element<0)}%>%map{(element*element)}")
        )
    }

    @Test(expected = ParserException::class)
    fun shouldThrowOnSyntaxErrorInput() {
        CallChainToFilterMap.doConversion("filter{(element>0)}%>%filter{(element<0)}%>%map{(element*element)")
    }

    @Test(expected = TypeErrorException::class)
    fun shouldThrowOnTypeErrorInput() {
        CallChainToFilterMap.doConversion("filter{(element>0)}%>%filter{(element<0)}%>%filter{(element*element)}")
    }
}