package com.github.galads

private fun termGreen() = "\u001B[32m"

private fun termPurple() = "\u001B[35m"

private fun termYellow() = "\u001B[33m"

private fun termReset() = "\u001B[0m"

private fun strIntException() = IllegalArgumentException("Argument should be Int")

fun fizzBuzz(start: Int, end: Int) {
    (start..end).forEach {
        val strPrint = when {
            it % 5 == 0 && it % 3 == 0 -> "${termGreen()}Fizz Buzz${termReset()}"
            it % 3 == 0 -> "${termPurple()}Fizz${termReset()}"
            it % 5 == 0 -> "${termYellow()}Buzz${termReset()}"
            else ->  "$it"
        }
        println(strPrint)
    }
}

object FizzBuzz {
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.size < 2)
            throw IllegalArgumentException("At least two arguments required: <start> <stop>")
        val start = args[0].toIntOrNull() ?: throw strIntException()
        val end = args[1].toIntOrNull() ?: throw strIntException()
        fizzBuzz(start, end)
    }
}