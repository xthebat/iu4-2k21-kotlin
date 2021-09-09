package ru.bmstu.iu4

object Program {
    @JvmStatic
    fun main(args: Array<String>) {
        val i = args[0].toInt()
        for (item in i..100) {
            if (item % 3 == 0 && item % 5 == 0) {
                println("FizzBuzz")
            } else if (item % 5 == 0) {
                println("Buzz")
            } else if (item % 3 == 0) {
                println("Fizz")
            } else println(item)
        }
    }
}