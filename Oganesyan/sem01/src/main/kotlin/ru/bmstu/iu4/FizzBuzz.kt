package ru.bmstu.iu4

object FizzBuzz {

    fun FizzBuzz(n: Int):String {
        return if(n % 3 == 0) {
            "Fizz"
        } else if(n % 5 == 0) {
            "Buzz"
        } else if(n % 15 == 0) {
            "FizzBuzz"
        } else {
            "$n"
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        for(i in 1..args[0].toInt()) {
            println(FizzBuzz(i))
        }
    }
}