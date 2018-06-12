package com.sam.lexical

import java.util.*
import java.util.Vector


class NFA(private var regex: String) {
    companion object {
        const val MAX_NODE = 100
    }

    var finalState = BooleanArray(MAX_NODE)
    private var cntOfNode = 1
    private var endNode = TreeMap<Int, Int>()
    private var allNodes = ArrayList<Edge>()
    private var nfaGraph = arrayOfNulls<Vector<Pair>>(MAX_NODE)//NFA图
    var characterSet = TreeSet<Char>()

    private fun addEdge(u: Int, v: Int, ch: Char) {
        allNodes.add(Edge(u, v, ch))
        if (nfaGraph[u] == null)
            nfaGraph[u] = Vector()
        nfaGraph[u]!!.add(Pair(v, ch))
        if (ch != 'ε')
            characterSet.add(ch)
    }

    private fun kernelWay(fa: Int, ld: Int, rd: Int, isClosure: Boolean): Boolean {
        if (ld < 0 || rd >= regex.length) {
            println("Regex Error")
            return false
        }
        var preNode = fa
        var inBracket = 0
        for (i in ld..rd) {
            when {
                regex[i] == '(' -> inBracket++
                regex[i] == ')' -> inBracket--
                regex[i] == '|' && inBracket == 0 -> {
                    if (!kernelWay(fa, ld, i - 1, isClosure))
                        return false
                    if (!kernelWay(fa, i + 1, rd, isClosure))
                        return false
                    return true
                }
            }
        }
        var i = ld
        while (i <= rd) {
            if (regex[i] == '(') {
                var cntLeftBracket = 0
                var posRightBracket = -1
                val posLeftBracket = i
                for (j in i + 1..rd) {
                    if (regex[j] == '(')
                        cntLeftBracket++
                    else if(regex[j] == ')') {
                        if (cntLeftBracket == 0) {
                            posRightBracket = j
                            break
                        }
                        cntLeftBracket--
                    }
                }
                if (posRightBracket == -1) {
                    println("Regex Error")
                    return false
                }
                var nodeFather: Int
                if (posRightBracket + 1 <= rd && regex[posRightBracket + 1] == '*') {
                    i = posRightBracket + 1
                    addEdge(preNode, ++cntOfNode, 'ε')
                    preNode = cntOfNode
                    nodeFather = cntOfNode
                    addEdge(preNode, ++cntOfNode, 'ε')
                    preNode = cntOfNode
                    if (!kernelWay(nodeFather, posLeftBracket + 1, posRightBracket - 1, true))
                        return false
                } else {
                    nodeFather = preNode
                    if (!kernelWay(nodeFather, posLeftBracket + 1, posRightBracket - 1, false))
                        return false
                    i = posRightBracket
                }
            } else {
                if (regex[i] == ')')
                    continue
                if (i + 1 <= rd && regex[i + 1] == '*') {
                    addEdge(preNode, ++cntOfNode, 'ε')
                    preNode = cntOfNode
                    addEdge(preNode, preNode, regex[i])
                    if (i + 1 == rd && isClosure)
                        addEdge(preNode, fa, 'ε')
                    else {
                        if (endNode.containsKey(fa))
                            addEdge(preNode, endNode[fa]!!, 'ε')
                        else {
                            addEdge(preNode, ++cntOfNode, 'ε')
                            if (i == rd)
                                endNode[fa] = cntOfNode
                        }
                    }
                    preNode = cntOfNode
                    ++i
                } else {
                    if (i == rd && isClosure) {
                        addEdge(preNode, fa, regex[i])
                    } else {
                        if (endNode.containsKey(fa))
                            addEdge(preNode, endNode[fa]!!, regex[i])
                        else {
                            addEdge(preNode, ++cntOfNode, regex[i])
                            if (i == rd)
                                endNode[fa] = cntOfNode
                        }
                    }
                    preNode = cntOfNode
                }
            }
            i++
        }
        return true
    }

    private fun checkFinalState() {
        for (i in 1..cntOfNode) {
            var cc = 0
            if (nfaGraph[i] == null) {
                finalState[i] = true
                continue
            }
            for (j in nfaGraph[i]!!.indices)
                if (nfaGraph[i]!!.elementAt(j).v != i)
                    ++cc
            if (cc == 0)
                finalState[i] = true
        }
    }

    fun getNFAGraphics(): Array<Vector<Pair>?>? {
        if (kernelWay(1, 0, regex.length - 1, false)) {
            checkFinalState()
            return nfaGraph
        }
        return null
    }

    fun outputNFA() {
        if (kernelWay(1, 0, regex.length - 1, false)) {
            checkFinalState()
            allNodes.sortWith(Comparator { o1, o2 ->
                if (o1.u == o2.u)
                    o1.v - o2.v
                else
                    o1.u - o2.u
            })
            for (e in allNodes)
                println(e)
        }
    }
}
fun main(args: Array<String>) {
    val regexList = ArrayList<String>()
    regexList.add("0*(100*)*0*")
    regexList.add("1(1010*|1(010)*1)*0")
    regexList.add("1(0|1)*101")
    regexList.add("0*1*(010)0*1*")
    for (regex in regexList) {
        val nfa = NFA(regex)
        println(regex)
        println("对应的NFA如下:")
        nfa.outputNFA()
        println()
    }
}