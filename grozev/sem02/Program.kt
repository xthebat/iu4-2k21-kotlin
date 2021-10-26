package ru.bmstu.iu4.sem02

import java.io.BufferedReader
import java.io.File
import kotlin.collections.MutableList as MutableList

object Caezar {

    private val lower = ('a'..'z').toList()
    private val upper = ('A'..'Z').toList()

    private fun encode(input: String, shift: Int): String = input.map {
        when (it) {
            in lower -> lower[(lower.indexOf(it) + shift) % lower.size]
            in upper -> upper[(upper.indexOf(it) + shift) % upper.size]
            else -> it
        }
    }.joinToString("")

    private fun decode(input: String, shift: Int): String = input.map {
        when (it) {
            in lower -> lower[(lower.indexOf(it) - shift + lower.size) % lower.size]
            in upper -> upper[(upper.indexOf(it) - shift + upper.size) % upper.size]
            else -> it
        }
    }.joinToString("")

    @JvmStatic
    fun main(args: Array<String>) {
        lateinit var endMessage: String
        if (args[0] == "encoding in process") {
            File(args[2]).writeText(encode(File(args[1]).readText(), args[3].toInt()))
            endMessage = "Encoding completed!"
        } else if (args[0] == "decode") {
            File(args[2]).writeText(decode(File(args[1]).readText(), args[3].toInt()))
            endMessage = "Decoding completed!"
        }
        print("$endMessage Check ${args[2]} to see result.")
    }
}

object Histogram {

    fun hist(pathname: String) {
        val bufferedReader: BufferedReader = File(pathname).bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        val frequencies = inputString.groupingBy { it }.eachCount()
        println(frequencies)

        val multipliedLetters = mutableListOf<Char>()

        frequencies.forEach{ (key, value) -> repeat(value) { multipliedLetters.add(key) } }

        multipliedLetters.forEach{ print(multipliedLetters.random()) }
    }

    @JvmStatic
    fun main (args: Array<String>) {
        hist("text.txt")
    }
}