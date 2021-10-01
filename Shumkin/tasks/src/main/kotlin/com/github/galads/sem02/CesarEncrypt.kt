package com.github.galads.sem02

import java.io.File

private fun mathEncrypt(chr: Char, start: Char, end: Char, buffer: MutableList<Char>, key: Int) {
    when {
        chr + key in start..end -> buffer.add(chr + key)
        chr + key > end -> {
            var value = chr + key
            val range = (start..end)
            while (value !in range) {
                value -= range.count()
            }
            buffer.add(value)
        }
    }
}

private fun mathDecrypt(chr: Char, start: Char, end: Char, buffer: MutableList<Char>, key: Int) {
    when {
        chr - key in start..end -> buffer.add(chr - key)
        else -> {
            var value = chr - key
            val range = (start..end)
            while (value !in range) {
                value += range.count()
            }
            buffer.add(value)
        }
    }
}

private fun encrypt(list: List<Char>, key: Int, buffer: MutableList<Char>) {
    list.forEach {
        when (it) {
            in ('A'..'Z') -> mathEncrypt(it, 'A', 'Z', buffer, key)
            in ('a'..'z') -> mathEncrypt(it, 'a', 'z', buffer, key)
            else -> buffer.add(it)
        }
    }
}

private fun decrypt(list: List<Char>, key: Int, buffer: MutableList<Char>) {
    list.forEach {
        when (it) {
            in ('A'..'Z') -> mathDecrypt(it, 'A', 'Z', buffer, key)
            in ('a'..'z') -> mathDecrypt(it, 'a', 'z', buffer, key)
            else -> buffer.add(it)
        }
    }
}

object CesarEncrypt {
    @JvmStatic
    fun main(arg: Array<String>) {
        if (arg.size != 4) {
            println("Error: not a valid arguments : need 4 args")
            return
        }

        val listAlphaIn = mutableListOf<Char>()
        File(arg[1]).readText()
            .let { it ->
                it.forEach { listAlphaIn.add(it) }
            }

        val buffer = mutableListOf<Char>()
        when {
            arg[0].equals("encrypt", true) -> encrypt(listAlphaIn, arg[3].toInt(), buffer)
            arg[0].equals("decrypt", true) -> decrypt(listAlphaIn, arg[3].toInt(), buffer)
            else -> println("Error: not a valid key argument")
        }

        File(arg[2]).printWriter().use { out ->
            buffer.forEach {
                out.print(it)
            }
        }
    }
}
// Test case
// encrypt src/main/kotlin/com/github/galads/sem02/Cesar.input src/main/kotlin/com/github/galads/sem02/output.file 751