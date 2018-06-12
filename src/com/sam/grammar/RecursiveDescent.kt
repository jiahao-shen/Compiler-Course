package com.sam.grammar

import java.util.ArrayList
import java.util.Scanner


class RecursiveDescent(private var str: String) {
    private var n = 0
    private var data = ArrayList<String>()
    private var flag = true

    init {
        check()
    }

    private fun num(string: String): Boolean {
        for (i in 0 until string.length) {
            if (string[i] in '0'..'9' || string[i] == '。' || string[i] == '.')
                continue
            else {
                return false
            }
        }
        return true
    }


    private fun eEquation() {
        if (data[n] == "(" || data[n] == "i") {
            tEquation()
            epEquation()
        } else {
            println("匹配失败")
            flag = false
        }
    }


    private fun epEquation() {
        if (data[n] == "+") {
            match("+")
            tEquation()
            epEquation()
        } else if (data[n] == "-") {
            match("-")
            tEquation()
            epEquation()
        } else if (data[n] == ")" || data[n] == "#") {
        } else {
            println("匹配失败")
            flag = false
        }
    }


    private fun tEquation() {
        if (data[n] == "(" || data[n] == "i") {
            fEquation()
            tpEquation()
        } else {
            println("匹配失败")
            flag = false
        }
    }


    private fun tpEquation() {
        if (data[n] == "*") {
            match("*")
            fEquation()
            tpEquation()
        } else if (data[n] == "/") {
            match("/")
            fEquation()
            tpEquation()
        } else if (data[n] == "+" || data[n] == "-" || data[n] == ")" || data[n] == "#") {
        } else {
            println("匹配失败")
            flag = false
        }
    }


    private fun fEquation() {
        when {
            data[n] == "(" -> {
                match("(")
                eEquation()
                match(")")
            }
            data[n] == "i" -> match("i")
            else -> {
                println("匹配失败")
                flag = false
            }
        }
    }


    private fun match(string: String) {
        if (data[n] == string) {
            n++
        } else {
            println("匹配失败")
            flag = false
        }
    }


    private fun deleteBlank(data: ArrayList<String>) {
        while (data.contains("")) {
            val n = data.indexOf("")
            data.removeAt(n)
        }
    }

    private fun check() {
        str += " #"
        str = str.replace("(", " ( ")
        str = str.replace(")", " ) ")
        str = str.replace("+", " + ")
        str = str.replace("-", " - ")
        str = str.replace("*", " * ")
        str = str.replace("/", " / ")
        str = str.replace("#", " # ")
        var s = ""
        for (i in 0 until str.length) {
            if (str[i] != ' ') {
                s += str[i]
            } else {
                data.add(s)
                s = ""
                continue
            }
        }
        deleteBlank(data)
        for (i in data.indices)
            if (num(data[i]))
                data[i] = "i"
        var z = 0
        var y = 0
        for (i in data.indices) {
            if (data[i] == "(")
                z++
            else if (data[i] == ")")
                y++
        }
        if (z != y) {
            println("表达式左右括号不匹配,请检查表达式")
            flag = false
        }
        eEquation()
        if (flag)
            println("匹配成功")
    }
}
fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)
    while (true) {
        println("请输入表达式:")
        val str = scan.nextLine()
        if (str == "exit")
            break
        RecursiveDescent(str)
        println()
    }
}