package com.github.galads

fun fizzBuzz(start: Int? = 1, end: Int? = 100) {
    if ((start is Int) && (end is Int)) {
        for (i in start..end) {
            println (
                when {
                    (i % 5 == 0 && i % 3 == 0) -> "\u001B[32mFizz Buzz\u001B[0m"
                    (i % 3) == 0 -> "\u001B[35mFizz\u001B[0m"
                    (i % 5) == 0 -> "\u001B[33mBuzz\u001B[0m"
                    else ->  i
                }
            )
        }
    } else
        throw Exception ("\u001B[31mError: argument is a \"null\"\u001B[0m")
}

object FizzBuzz {
    @JvmStatic
    fun main(args: Array<String>) {
        var num: Int? = null

        try {
            if (args.size > 1) { throw Exception("\u001B[31mError: more arguments\u001B[0m") }
            if (args.isNotEmpty()) { num = args[0].toInt() }
            fizzBuzz(num)
        } catch (e: Exception) {
            println(e)
        }
    }
}