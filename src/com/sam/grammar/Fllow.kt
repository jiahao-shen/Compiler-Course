package com.sam.grammar

import java.util.*

class Follow(private var mp: Map<String, Array<String>>, private var first: Map<String, Set<Char>>) {
    private val follow = TreeMap<String, TreeSet<Char>>()

    private fun getFirstSet(st: MutableSet<Char>, node: String, temp: Int) {
        var k = temp
        if (k >= node.length) return
        if (node[k] == '\'') --k
        var nextNode = "" + node[k]
        if (k + 1 < node.length && node[k + 1] == '\'') {
            nextNode += '\''
            ++k
        }
        if (!mp.containsKey(nextNode)) {//终结点
            st.add(nextNode[0])
        } else {
            st.addAll(first[nextNode]!!)
            if (first[nextNode]!!.contains('ε'))
                getFirstSet(st, node, k + 1)
        }
    }

    private fun findFollow(currentNode: String) {
        var st: TreeSet<Char>?
        for (leftNode in mp.keys) {
            val rightNodes = mp[leftNode]
            for (i in rightNodes!!.indices) {
                var index = rightNodes[i].indexOf(currentNode, 0)
                while (index != -1) {
                    val nextIndex = index + 1
                    if (currentNode.length == 1 && index + 1 < rightNodes[i].length && rightNodes[i][index + 1] == '\'') {
                        index = rightNodes[i].indexOf(currentNode, nextIndex)
                        continue
                    }
                    index += currentNode.length
                    if (index == rightNodes[i].length) {//末尾的非终结点, A->@B
                        if (follow[leftNode] == null)
                            findFollow(leftNode)
                        if (follow[currentNode] == null) {
                            st = TreeSet()
                            st.addAll(follow[leftNode]!!)
                            follow[currentNode] = st
                        } else
                            follow[currentNode]!!.addAll(follow[leftNode]!!)
                    } else {
                        var nextNode = "" + rightNodes[i][index]
                        if (index + 1 < rightNodes[i].length && rightNodes[i][index + 1] == '\'') {
                            nextNode += '\''.toString()
                            ++index
                        }
                        if (mp.containsKey(nextNode)) {
                            if (first[nextNode]!!.contains('ε')) {
                                if (follow[leftNode] == null)
                                    findFollow(leftNode)
                                if (follow[currentNode] == null) {
                                    st = TreeSet()
                                    st.addAll(follow[leftNode]!!)
                                    follow[currentNode] = st
                                } else
                                    follow[currentNode]!!.addAll(follow[leftNode]!!)
                            }

                            run {
                                val tempSet = TreeSet<Char>()
                                getFirstSet(tempSet, rightNodes[i], index)
                                tempSet.remove('ε')
                                if (follow[currentNode] == null) {
                                    st = TreeSet()
                                    st!!.addAll(tempSet)
                                    follow[currentNode] = st!!
                                } else
                                    follow[currentNode]!!.addAll(tempSet)
                            }
                        } else {
                            if (follow[currentNode] == null) {
                                st = TreeSet()
                                st!!.add(nextNode[0])
                                follow[currentNode] = st!!
                            } else
                                follow[currentNode]!!.add(nextNode[0])
                        }
                    }
                    index = rightNodes[i].indexOf(currentNode, nextIndex)
                }
            }
        }
    }

    fun followKernealCode(): String {
        var content = ""
        var flag = true
        for (leftNode in mp.keys) {
            if (flag) {
                val st = TreeSet<Char>()
                st.add('#')
                follow[leftNode] = st
                flag = false
            }
            findFollow(leftNode)
        }

        println("Follow集如下:")
        for ((key, value) in follow) {
            content += "$key : $value\n"
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
//    val rightLinearGrammar = arrayOf(
//            "S->ABc",
//            "A->a|ε",
//            "B->b|ε"
//    )
    val mp = LinkedHashMap<String, Array<String>>()
    try {
        for (i in rightLinearGrammar.indices) {
            val split1 = rightLinearGrammar[i].split("->".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val split2 = split1[1].split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            mp[split1[0]] = split2
        }

    } catch (e: Exception) {
        e.printStackTrace()
        println("右线性文法错误!")
    }

    val first = First(mp)
    first.firstKernealCode()
    Follow(mp, first.first).followKernealCode()
}