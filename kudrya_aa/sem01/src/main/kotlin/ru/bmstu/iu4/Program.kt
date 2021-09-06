package ru.bmstu.iu4

object Program {
    @JvmStatic
    fun main(args: Array<String>) {
        fizzbuzz(args[0])
    }
    private fun fizzbuzz(str:String) {
        for (it in str.toInt()..100) {
            val num:String = if (it % 3 == 0 && it % 5 == 0) "Fizz Buzz"
            else if(it % 3 == 0) "Fizz"
            else if(it % 5 == 0) "Buzz"
            else it.toString()
            println(num)
        }
    }
}