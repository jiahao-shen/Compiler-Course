package com.sam.grammar

class Grammar(var left: String, var right: String) {
    private var id = 0
    init {
        id = ID++
    }
    companion object {
        private var ID = 0
    }

    override fun toString(): String {
        return "$left -> $right"
    }
}