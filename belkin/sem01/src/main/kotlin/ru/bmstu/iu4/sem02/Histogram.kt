package ru.bmstu.iu4.sem02

import java.io.BufferedReader
import java.io.File

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