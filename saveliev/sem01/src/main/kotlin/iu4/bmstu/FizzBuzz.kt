package iu4.bmstu

object FizzBuzz {
    fun fizzBuzz(start: Int) {
        val result = when {
            (start % 5 == 0) && (start % 3 == 0) -> "FizzBuzz"
            start % 3 == 0 -> "Fizz"
            start % 5 == 0 -> "Buzz"
            else -> start.toString()
        }
        print("$result ")
    }

    @JvmStatic
    fun main(args: Array<String>) {
        var number = args[0].toInt()
        for (it in number..100)
            fizzBuzz(it)
    }
}