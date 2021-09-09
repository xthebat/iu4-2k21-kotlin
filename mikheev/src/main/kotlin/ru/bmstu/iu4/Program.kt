package ru.bmstu.iu4

object Program {
    fun fizzbuzz(a: Int){
        if(a%5 == 0 && a%3 == 0)
            println("fizz buzz")
        else if(a%3 == 0)
            println("fizz")
        else if(a%5 == 0)
            println("buzz")
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val a = args[0].toInt()
        for(i in 1..a) {
            fizzbuzz(i)
        }
    }
}