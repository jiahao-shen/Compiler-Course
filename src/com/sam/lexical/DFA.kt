package com.sam.lexical

import java.util.*


class DFA(private var nfaGraph: Array<Vector<Pair>?>?, var characterSet: Set<Char>, private var finalState: BooleanArray) {
    var dfaNode = 0
    var newFinalState = BooleanArray(NFA.MAX_NODE)
    private var edgeSet = HashSet<Edge>()
    private var st: MyHashSet = MyHashSet()
    private var queue = LinkedList<MyHashSet>()
    private var sst = HashSet<MyHashSet>()
    var allNodes = ArrayList<Edge>()

    private fun dfs(u: Int, ch: Char) {
        nfaGraph?.get(u)?.let {
            for (i in it.indices) {
                val pair = it.elementAt(i)
                val edge = Edge(u, pair.v, pair.ch)
                if (!edgeSet.contains(edge) && pair.ch == ch) {
                    edgeSet.add(edge)
                    st.add(pair.v)
                    dfs(pair.v, 'ε')
                }
            }
        }
    }

    private fun checkIsFinalState(st: Set<Int>, state: Int) {
        for (item in st) {
            if (finalState[item])
                newFinalState[state] = true
        }
    }

    private fun initFirstSet() {
        edgeSet.clear()
        st = MyHashSet()
        st.add(1)
        st.state = ++dfaNode
        dfs(1, 'ε')
        checkIsFinalState(st, dfaNode)
        sst.add(st)
        queue.add(st)
    }

    private fun addEdge(u: Int, v: Int, ch: Char) {
        allNodes.add(Edge(u, v, ch))
    }

    fun toStateMatrix() {
        initFirstSet()
        while (!queue.isEmpty()) {
           val mySet = queue.poll()
            for (ch in characterSet) {
                st = MyHashSet()
                for (i in mySet) {
                    edgeSet.clear()
                    dfs(i, ch)
                }
                if (st.size > 0) {
                    if (!sst.contains(st)) {
                        sst.add(st)
                        queue.add(st)
                        st.state = ++dfaNode
                        checkIsFinalState(st, dfaNode)
                    } else {
                        for (item in sst) {
                            if (item == st) {
                                st = item
                                break
                            }
                        }
                    }
                    addEdge(mySet.state, st.state, ch)
                }
            }
        }
    }

    fun outputDFA() {
        toStateMatrix()
        allNodes.sortWith(Comparator { o1, o2 ->
            if (o1.u == o2.u)
                o1.v - o2.v
            else
                o1.u - o2.u
        })
        for (edge in allNodes) {
            println(edge)
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
        val dfa = DFA(nfa.getNFAGraphics(), nfa.characterSet, nfa.finalState)
        println(regex)
        println("对应的DFA(未最小化)如下:")
        dfa.outputDFA()
        println()
    }
}