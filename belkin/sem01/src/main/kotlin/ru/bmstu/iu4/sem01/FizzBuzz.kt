package ru.bmstu.iu4.sem01

object FizzBuzz {
    fun FizzBuzz(start: String) {
        for (number in start.toInt()..100) {
            if (number % 3 == 0 && number % 5 == 0) println("FizzBuzz")
            else if (number % 3 == 0) println("Fizz")
            else if (number % 5 == 0) println("Buzz")
            else println(number)
        }
    }

    @JvmStatic
    fun main(args: Array<String>){
        FizzBuzz(args[0])
    }
}