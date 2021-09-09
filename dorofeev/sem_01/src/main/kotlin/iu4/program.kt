package ru.bmstu.iu4

object Program {
    @JvmStatic
    fun main(args: Array<String>) = fizz_buzz(args.last().toInt())

    fun fizz_buzz(start_point: Int) {
        for (i in start_point..100) when {
            i % 15 == 0 -> println("fizz buzz")
            i % 5 == 0 -> println("buzz")
            i % 3 == 0 -> println("fizz")
            else -> println(i)
        }


    }

}