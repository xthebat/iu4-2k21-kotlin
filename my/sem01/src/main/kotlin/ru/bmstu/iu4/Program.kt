package ru.bmstu.iu4


object Program {

    fun max(a: Long, b: Long, c: Long) = if (a > b) {
        if (a > c) a else c
    } else {
        if (b > c) b else c
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("meow-world!")

        val byte = 10.toByte()
        val short: Short = 10
        val s1: Long = 10
        val int: Int = 10

        var long = 10L

        long = short.toLong()

        val string = "324234"
        val char = '∑'
        val float = 10.0f
        val double = 10.0

        val tr = true
        val fl = false

        val u: ULong = 10u

        var nullableInt: Int? = 10

//        nullableInt = null

        val s2 = int + long

        val s3 = int + (nullableInt ?: 0)

        val s4 = if (s3 < 10) {
            val x = string.length
            x + 10
        } else {
            10
        }

        val s5 = when (char) {
            '1' -> 10
            '2' -> 20
            '3' -> 30
            else -> 100
        }

        val s6: Int
        when (char) {
            '1' -> s6 = 10
            else -> s6 = 100
        }

        when (int) {
            in 10..100 -> {
                println()
            }
            200 -> {

            }
            300 -> {

            }
        }

        println("for example")

        for (k in 10..100) {
            println(k)
        }

        println("while example")

        var k = 0
        while (k < args.size) {
            println(args[k])
            k++
            when (k) {
                4 -> continue
                5 -> println(10)
                6 -> break
            }
        }

        println("for example 2")

        for (k in 0 until args.size) {
            println(args[k])
        }

        println("for example 3")

        for ((i, arg) in args.withIndex()) {
            val j = 2 * i
            println("$j $i $arg")
            break
        }

        println("function test")

        val y = max(10, 20, 30)
        println(y)


        println("foreach exampl")
        args.take(6).forEach { println(it) }

/*
#include <stdio.h>

void main(void) {
    for (int i = 0; i < 10; printf("%d\n", i++));
}
*/
    }

}