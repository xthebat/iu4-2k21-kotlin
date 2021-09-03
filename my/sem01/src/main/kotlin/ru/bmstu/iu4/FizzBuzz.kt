package ru.bmstu.iu4

object FizzBuzz {

    fun FizzBuzz(args: Array<String>) {
        for (k in 0 until args.size) {
            if (args[k].toInt() % 15 == 0) println("FizzBuzz")
            else if (args[k].toInt() % 3 == 0) println("Fizz")
            else if (args[k].toInt() % 5 == 0) println("Buzz")
            else println(args[k])
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        FizzBuzz(args)
    }
}
