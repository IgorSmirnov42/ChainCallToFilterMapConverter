package ru.smirnov.intership.r.test.grammar.components

class CallChain(val calls: List<Call>) {
    fun <T> accept(visitor: ComponentVisitor<T>): T? {
        return visitor.visitCallChain(this)
    }

    override fun equals(other: Any?): Boolean {
        return other is CallChain && other.calls == this.calls
    }

    override fun toString(): String {
        return calls.joinToString(separator = "%>%")
    }
}

class CallChainBuilder {
    private val calls = mutableListOf<Call>()

    fun add(newCall: Call) {
        calls.add(newCall)
    }

    fun build(): CallChain {
        return CallChain(calls)
    }
}