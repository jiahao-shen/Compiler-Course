package com.sam.syntax

import java.util.*

class Production(private var productions: ArrayList<Grammar>) {
    private val symbols = ArrayList<Char>()
    private val nonTerminatingSymbol = TreeSet<String>()
    private val terminatingSymbol = TreeSet<String>()

    init {
        symbolProductions()
    }

    fun removeLeftRecursion() {
        for (i in symbols.indices) {
            for (j in 0 until i) {
                iterativeReplacement(symbols[i], symbols[j])
            }
            removeLeftRecursion(symbols[i])
        }
        noOrIsTerminatingSymbol()
        output()
    }

    private fun symbolProductions() {
        if (productions.size != 0) {
            for (i in productions.indices) {
                if (!symbols.contains(productions[i].left[0])) {
                    symbols.add(productions[i].left[0])
                }
            }
        }
    }

    private fun noOrIsTerminatingSymbol() {
        for (i in productions.indices) {
            if (!nonTerminatingSymbol.contains(productions[i].left)) {
                nonTerminatingSymbol.add(productions[i].left)
            }
            if (productions[i].left == "${productions[i].left[0]}'") {
                nonTerminatingSymbol.add(productions[i].left)
            }
        }
        for (i in productions.indices) {
            var temp = productions[i].right
            temp = temp.replace("ε", "#")
            for (item in nonTerminatingSymbol) {
                temp = temp.replace(item.toRegex(), "")
            }
            temp = temp.replace("\\|".toRegex(), "")
            temp = temp.replace("'".toRegex(), "")
            val chars = temp.toCharArray()
            for (k in chars.indices) {
                if (chars[k] == '#') {
                    if (!terminatingSymbol.contains("ε")) {
                        terminatingSymbol.add("ε")
                    }
                } else {
                    if (!terminatingSymbol.contains(chars[k].toString())) {
                        terminatingSymbol.add(chars[k].toString())
                    }
                }
            }
        }
    }

    private fun iterativeReplacement(left: Char, right: Char) {
        for (item1 in productions) {
            var inRight = ""
            if (item1.left == left.toString()) {
                var isReplacement = false
                val rights1 = item1.right.split("\\|".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                for (i in rights1.indices) {
                    if (rights1[i].startsWith(right.toString())) {
                        isReplacement = true
                    }
                }
                if (isReplacement) {
                    for (item2 in productions) {
                        if (item2.left == right.toString()) {
                            val rights2 = item2.right.split("\\|".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                            for (i in rights1.indices) {
                                var isCheck = false
                                if (rights1[i].startsWith(right.toString())) {
                                    isCheck = true
                                    for (j in rights2.indices) {
                                        val temp = rights1[i]
                                        inRight += temp.replaceFirst(right.toString().toRegex(), rights2[j]) + "|"
                                    }
                                }
                                if (!isCheck) {
                                    inRight += rights1[i] + "|"
                                }
                            }
                        }
                    }
                    if (inRight.isNotEmpty()) {
                        productions.removeAt(productions.lastIndex)
                        productions.add(Grammar(left.toString(), inRight.substring(0, inRight.length - 1)))
                    }
                }
            }
        }
    }

    private fun removeLeftRecursion(left: Char) {
        for (index in productions.indices) {
            val grammar = productions[index]
            if (grammar.left == left.toString()) {
                val rights = grammar.right.split("\\|".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                var isLeftRecursion = false
                for (i in rights.indices) {
                    if (rights[i].startsWith(left.toString())) {
                        isLeftRecursion = true
                    }
                }
                if (isLeftRecursion) {
                    productions.removeAt(index)
                    var oneRight = ""
                    var twoRight = ""
                    for (i in rights.indices) {
                        if (!rights[i].startsWith(left.toString())) {
                            oneRight += rights[i] + (left.toString() + "'") + "|"
                        } else {
                            twoRight += rights[i].replaceFirst(left.toString().toRegex(), "") + (left.toString() + "'") + "|"
                        }
                    }
                    productions.add(Grammar(left.toString(), oneRight.substring(0, oneRight.length - 1)))
                    productions.add(Grammar(left.toString() + "'", "${twoRight}ε"))
                }
            }
        }
    }

    private fun output() {
        println("非终结符: $nonTerminatingSymbol")
        println("终结符: $terminatingSymbol")
        println("消除左递归后的文法:")
        for (item in productions) {
            println(item)
        }
    }

}

fun main(args: Array<String>) {
    val grammarList = ArrayList<Grammar>()
//    grammarList.add(Grammar("S", "Qc|c"))
//    grammarList.add(Grammar("Q", "Rb|b"))
//    grammarList.add(Grammar("R", "Sa|a"))
    grammarList.add(Grammar("E", "E+T|T"))
    grammarList.add(Grammar("T", "T*F|F"))
    grammarList.add(Grammar("F", "(E)|i"))
    val production = Production(grammarList)
    production.removeLeftRecursion()
}
