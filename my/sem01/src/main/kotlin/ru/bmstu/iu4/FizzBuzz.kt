package ru.bmstu.iu4

object FizzBuzz {

    private fun fizzBuzz(args: Array<String>) {
        if (args.size == 2) {
            for (iter in args[0].toInt()..args[1].toInt()) {
                if (iter % 15 == 0) println("FizzBuzz")
                else if (iter % 3 == 0) println("Fizz")
                else if (iter % 5 == 0) println("Buzz")
                else println(iter)
            }
        } else println("You need to enter left and right bound of the subsequence. Try Again!")
    }

    @JvmStatic
    fun main(args: Array<String>) {
        fizzBuzz(args)
    }
}
