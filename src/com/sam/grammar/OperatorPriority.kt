package com.sam.grammar

import java.math.BigDecimal
import java.util.*
import java.util.regex.Pattern
import kotlin.NoSuchElementException

class OperatorPriority(private var str: String) {
    private var flag = true

    init {
        evaluateExpression()
    }
    private fun evaluateExpression() {
        str = "#$str#"

        val operandBuffer = StringBuffer()
        val operandList = LinkedList<BigDecimal>()
        val operatorList = LinkedList<String>()

        var count = 1
        operatorList.addLast("#")
        while (count < str.length) {
            val ch = str[count].toString()
            if (Pattern.matches("[0-9.]", ch) || (ch == "-" && (count == 1 || str[count - 1] == '('))) {
                operandBuffer.append(ch)
                ++count
            } else {
                if (Pattern.matches("[+\\-*/)#]", ch) && operandBuffer.isNotEmpty()) {
                    operandList.addLast(BigDecimal(java.lang.Double.valueOf(operandBuffer.toString()).toString()))
                    operandBuffer.delete(0, operandBuffer.length)
                }

                when (compareOperator(operatorList.last, ch)) {
                    '<' -> {
                        operatorList.addLast(ch)
                        ++count
                    }
                    '=' -> {
                        operatorList.removeLast()
                        ++count
                    }
                    '>' -> {
                        val b = operandList.removeLast()
                        val a = operandList.removeLast()
                        val curOperator = operatorList.removeLast()
                        operandList.addLast(operate(a, curOperator, b))
                    }
                    else -> {
                        println("输入有误")
                        return
                    }
                }
            }
        }
        if (flag)
            println(operandList.last)
    }

    private fun compareOperator(operator1: String, operator2: String): Char {
        var result = ' '
        val o1 = operator1[0]
        val o2 = operator2[0]

        when (o1) {
            '+' -> when (o2) {
                '+' -> result = '>'
                '-' -> result = '>'
                '*' -> result = '<'
                '/' -> result = '<'
                '(' -> result = '<'
                ')' -> result = '>'
                '#' -> result = '>'
            }
            '-' -> when (o2) {
                '+' -> result = '>'
                '-' -> result = '>'
                '*' -> result = '<'
                '/' -> result = '<'
                '(' -> result = '<'
                ')' -> result = '>'
                '#' -> result = '>'
            }
            '*' -> when (o2) {
                '+' -> result = '>'
                '-' -> result = '>'
                '*' -> result = '>'
                '/' -> result = '>'
                '(' -> result = '<'
                ')' -> result = '>'
                '#' -> result = '>'
            }
            '/' -> when (o2) {
                '+' -> result = '>'
                '-' -> result = '>'
                '*' -> result = '>'
                '/' -> result = '>'
                '(' -> result = '<'
                ')' -> result = '>'
                '#' -> result = '>'
            }
            '(' -> when (o2) {
                '+' -> result = '<'
                '-' -> result = '<'
                '*' -> result = '<'
                '/' -> result = '<'
                '(' -> result = '<'
                ')' -> result = '='
                '#' -> result = '?'
            }
            ')' -> when (o2) {
                '+' -> result = '>'
                '-' -> result = '>'
                '*' -> result = '>'
                '/' -> result = '>'
                '(' -> result = '?'
                ')' -> result = '>'
                '#' -> result = '>'
            }
            '#' -> when (o2) {
                '+' -> result = '<'
                '-' -> result = '<'
                '*' -> result = '<'
                '/' -> result = '<'
                '(' -> result = '<'
                ')' -> result = '?'
                '#' -> result = '='
            }
        }
        return result
    }

    private fun operate(a: BigDecimal, operator: String, b: BigDecimal): BigDecimal? {
        val divScale = 10
        var result = BigDecimal(0)
        try {
            when (operator[0]) {
                '+' -> result = a.add(b)
                '-' -> result = a.subtract(b)
                '*' -> result = a.multiply(b)
                '/' -> result = a.divide(b, divScale, BigDecimal.ROUND_HALF_UP)
            }
        } catch (e: ArithmeticException) {
            println("不能除0")
            flag = false
        }
        return result
    }
}

fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)
    while (true) {
        println("输入表达式:")
        val str = scan.nextLine()
        if (str == "exit")
            break
        try {
            OperatorPriority(str)
        } catch (e: NoSuchElementException) {
            println("输入有误")
        }
        println()
    }
}