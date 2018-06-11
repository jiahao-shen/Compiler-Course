package com.sam.lexical

import java.util.HashSet


internal class MyHashSet : HashSet<Int>() {
    var state: Int = 0

    override fun equals(other: Any?): Boolean {
        val tmp = other as MyHashSet?
        if (tmp!!.size != this.size) return false
        val it = this.iterator()
        while (it.hasNext()) {
            if (!tmp.contains(it.next())) return false
        }
        return true
    }

    override fun hashCode(): Int {
        var sum = 0
        val it = this.iterator()
        while (it.hasNext())
            sum += it.next()
        return sum
    }
}