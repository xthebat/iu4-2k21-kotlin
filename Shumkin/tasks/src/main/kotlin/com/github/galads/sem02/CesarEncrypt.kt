package com.github.galads.sem02

import java.io.File

//private fun encrypt(list: List<Char>, key: Int, buffer: MutableList<Char>) {
////    val buffer = mutableListOf<Char>()
//    list.forEach {
//        when {
//            it.isLetter() -> when {
//                it + key in 'A'..'Z' -> buffer.add(it + key)
//                it + key > 'Z' -> {
//                    var value = it + key
//                    val range = ('A'..'Z')
//                    while (value !in range) {
//                        value -= range.count()
//                    }
//                    buffer.add(value)
//                }
//            }
//            else -> buffer.add(it)
//        }
//    }
//}

private fun mathEncrypt(chr: Char,range: CharRange ) {

}

private fun encrypt(list: List<Char>, key: Int, buffer: MutableList<Char>) {
    list.forEach {
        when {
            it.isLetter() -> when {
                it + key in 'A'..'Z' -> buffer.add(it + key)
                it + key > 'Z' -> {
                    var value = it + key
                    val range = ('A'..'Z')
                    while (value !in range) {
                        value -= range.count()
                    }
                    buffer.add(value)
                }
            }
            else -> buffer.add(it)
        }
    }
}

private fun uncrypt(list: List<Char>, key: Int, buffer: MutableList<Char>) {

}

object CesarEncrypt {
    @JvmStatic
    fun main(arg: Array<String>) {
        if (arg.size != 4) {
            println("Error: not a valid arguments : need 4 args")
            return
        }
/////////////////////////
        var str = ('С' + 0)
//        println(str.isLetter())
        println("""----> ${str.code}""")
        if (str > 'Я') {
            str = 'А'
        }
        println("----> $str")
//////////////////////////
        val listAlphaIn = mutableListOf<Char>()

        File(arg[1])
            .readText()
            .let { it ->
                it.forEach { listAlphaIn.add(it) }
            }
        val buffer = mutableListOf<Char>()


        when {
            arg[0].equals("encrypt", true) -> encrypt(listAlphaIn, arg[3].toInt(), buffer)
            arg[0].equals("uncrypt", true) -> uncrypt(listAlphaIn, arg[3].toInt(), buffer)
            else -> println("Error: not a valid key argument")
        }
        println(buffer)
        File(arg[2]).printWriter().use { out ->
            buffer.forEach {
                out.print(it)
            }
        }
    }
}