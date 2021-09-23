package lec

fun processor(it: Int){
    if (it >= 2)
        println(it)
}

object Program {
    @JvmStatic
    fun main(args: Array<String>) {
        val list = listOf(1, 2, 3)
        list.forEach(::processor)
    }
}