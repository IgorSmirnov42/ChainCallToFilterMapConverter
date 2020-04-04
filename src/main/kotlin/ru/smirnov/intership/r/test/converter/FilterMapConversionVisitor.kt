package ru.smirnov.intership.r.test.converter

import ru.smirnov.intership.r.test.grammar.components.*

/**
 * Visitor for conversion from chain to <filter-call> <map-call> form
 */
class FilterMapConversionVisitor : ComponentVisitor<CallChain> {
    private var currentFilter: Expression? = null
    private var currentMap: Expression = ElementExpression

    override fun visitFilterCall(call: FilterCall): CallChain? {
        val newFilterExpression = call.expression.accept(SubstituteElementVisitor(currentMap))!!
        currentFilter = if (currentFilter == null) {
            newFilterExpression
        } else {
            And(currentFilter!!, newFilterExpression)
        }
        return null
    }

    override fun visitMapCall(call: MapCall): CallChain? {
        currentMap = call.expression.accept(SubstituteElementVisitor(currentMap))!!
        return null
    }

    override fun visitCallChain(callChain: CallChain): CallChain? {
        super.visitCallChain(callChain)
        if (currentFilter == null) {
            currentFilter = Equality(ConstantExpression("1"), ConstantExpression("1"))
        }
        return CallChain(listOf(FilterCall(currentFilter!!), MapCall(currentMap)))
    }
}

/**
 * Visitor that substitutes all `Element` occurrences to `newElement`.
 *
 * Does not change tree. Creates new tree.
 */
private class SubstituteElementVisitor(private val newElement: Expression) : ComponentVisitor<Expression> {
    override fun visitBinaryExpression(expression: BinaryExpression): Expression? {
        return BinaryExpression.create(expression.operationString(),
                                expression.leftExpression.accept(this)!!,
                                expression.rightExpression.accept(this)!!)
    }

    override fun visitElementExpression(expression: ElementExpression): Expression? {
        return newElement
    }

    override fun visitConstantExpression(expression: ConstantExpression): Expression? {
        return expression
    }
}
