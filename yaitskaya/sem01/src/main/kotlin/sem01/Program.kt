package sem01

object Program {
    @JvmStatic
    fun main(args: Array<String>) {
        for (n in 1..100) {
            fizzbuzz(n)
        }
    }

    fun fizzbuzz(n: Int) {
        val result = if (n % 3 == 0) {
            if (n % 5 == 0) "FizzBuzz" else "Fizz"
        } else {
            if (n % 5 == 0) "Buzz" else n.toString()
        }
        print("FizzBuzz for $n:\t")
        println(result)
    }
}