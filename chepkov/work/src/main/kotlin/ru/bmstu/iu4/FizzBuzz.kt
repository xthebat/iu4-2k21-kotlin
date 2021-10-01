package ru.bmstu.iu4

object FizzBuzz {
    private fun fizzbuzz(current: Int): String {
        return if (current % 15 == 0) "Fizz Buzz"
        else if (current % 3 == 0) "Fizz"
        else if (current % 5 == 0) "Buzz"
        else current.toString()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        if (args.size == 2) {
            val start = args[0].toInt()
            val end = args[1].toInt()
            if (start in 1 until end)
                (start..end).forEach {
                    print("${fizzbuzz(it)} ")
                }
            else println("Диапазон введён неверно.")
        } else println("Пожалуйста, укажите два аргумента: начало отсчёта и конец.")
    }
}

