package ru.bmstu.ru

object Main{
    fun fizzbuzz(i: Int) {
        if (i%3 == 0 && i%5 == 0) {
            println("fizzbuzz")
            else if (i%3 == 0)
                println("fizz")
            else if (i%5 == 0)
                println("buzz")
        }
        fun main(args: Array<String>) {
            val i = args[0].toInt()
            for (a in 1..i) {
                fizzbuzz(a)
            }
        }
    }}