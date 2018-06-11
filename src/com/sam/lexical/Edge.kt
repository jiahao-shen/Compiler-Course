package com.sam.lexical

class Edge(var u: Int, var v: Int, var key: Char) {
    override fun toString(): String {
        return "$u -> $v : $key"
    }

    override fun equals(other: Any?): Boolean {
        val temp = other as Edge
        return temp.u == this.u && temp.v == this.v && temp.key == this.key
    }

    override fun hashCode(): Int {
        return u + v + key.toInt()
    }
}
