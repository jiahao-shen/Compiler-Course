package com.sam.grammar

import java.util.LinkedHashMap
import java.util.TreeSet
import java.util.TreeMap

class First(private var mp: Map<String, Array<String>>) {
    var first = TreeMap<String, Set<Char>>()

    private fun findFirst(currentNode: String, rightNodes: Array<String>) : Set<Char>{
        if (first.containsKey(currentNode))
            return first[currentNode]!!
        val st = TreeSet<Char>()
        for (i in rightNodes.indices) {
            var j = 0
            while (j < rightNodes[i].length) {
                var nextNode = "${rightNodes[i][j]}"
                if (!mp.containsKey(nextNode)) {
                    st.add(nextNode[0])
                    break
                } else {
                    if (j + 1 < rightNodes[i].length && rightNodes[i][j + 1] == '\'') {
                        nextNode += rightNodes[i][j + 1]
                        ++j
                    }
                    if (mp.containsKey(nextNode)) {
                        val tempSet = findFirst(nextNode, mp[nextNode]!!)
                        st.addAll(tempSet)
                        if (!tempSet.contains('ε'))
                            break
                    }
                }
                ++j
            }
        }
        first[currentNode] = st
        return st
    }

    fun firstKernealCode() : String {
        var content = ""
        for (leftNode in mp.keys) {
            val rightNode = mp[leftNode]
            findFirst(leftNode, rightNode!!)
        }
        println("First集如下:")
        for ((key, value) in first) {
            content += "$key : $value"
            println("$key : $value")
        }
        return content
    }
}
fun main(args: Array<String>) {
    val rightLinearGrammar = arrayOf(
            "E->TE\'",
            "E\'->+TE\'|ε",
            "T->FT\'",
            "T\'->*FT\'|ε",
            "F->(E)|i"
    )
//    val rightLinearGrammar = arrayOf("S->ABc", "A->a|ε", "B->b")
    val mp = LinkedHashMap<String, Array<String>>()
    try {
        for (i in rightLinearGrammar.indices) {
            val split1 = rightLinearGrammar[i].split("->".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val split2 = split1[1].split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            mp[split1[0]] = split2
        }
    } catch (e: Exception) {
        e.printStackTrace()
        println("左线性文法错误")
    }
    val first = First(mp)
    first.firstKernealCode()
}
