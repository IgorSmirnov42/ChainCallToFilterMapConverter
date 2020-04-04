package ru.smirnov.intership.r.test.grammar.components

/**
 * One sequence of command to be executed on array.
 */
data class CallChain(val calls: List<Call>) {
    fun <T> accept(visitor: ComponentVisitor<T>): T? {
        return visitor.visitCallChain(this)
    }

    override fun toString(): String {
        val builder = StringBuilder()
        for ((call, id) in (calls zip calls.indices)) {
            if (id != 0) {
                builder.append("%>%")
            }
            call.buildString(builder)
        }
        return builder.toString()
    }
}

/**
 * Simple builder for `CallChain` that adds calls to list and creates `CallChain`.
 */
class CallChainBuilder {
    private val calls = mutableListOf<Call>()

    /** Adds `newCall` to the back of list of calls */
    fun add(newCall: Call) {
        calls.add(newCall)
    }

    fun build(): CallChain {
        return CallChain(calls)
    }
}