package ru.bmstu.iu4.sem02

import java.io.BufferedReader
import java.io.File
import kotlin.collections.MutableList as MutableList

object Caesar {

    val upperAlphabet = ('A'..'Z').toMutableList()
    val lowerAlphabet = ('a'..'z').toMutableList()

    @JvmStatic
    fun main(args: Array<String>) {
        val bufferedReader: BufferedReader = File(args[1]).bufferedReader()
        val textIn = bufferedReader.use { it.readText() }

        if (args[0] == "encode") encode(textIn, args[2], args[3].toInt())
        else if (args[0] == "decode") decode(textIn, args[2], args[3].toInt())
        else println("Wrong arguments")

    }

    private fun encode(textIn: String, fileOut: String, key: Int) {
        val encodedCaesar: MutableList<Char> = mutableListOf()
        textIn.forEach { letter ->
            if (letter in upperAlphabet) {
                if (upperAlphabet.indexOf(letter) + key <= 25) {
                    encodedCaesar.add(upperAlphabet[upperAlphabet.indexOf(letter) + key])
                } else encodedCaesar.add(upperAlphabet[(upperAlphabet.indexOf(letter) + key) % 26])
            } else if (letter in lowerAlphabet) {
                if (lowerAlphabet.indexOf(letter) + key <= 25) {
                    encodedCaesar.add(lowerAlphabet[lowerAlphabet.indexOf(letter) + key])
                } else encodedCaesar.add(lowerAlphabet[(lowerAlphabet.indexOf(letter) + key) % 26])
            } else encodedCaesar.add(letter)
        }

        File(fileOut).writeText(encodedCaesar.joinToString(""))
    }

    private fun decode(textIn: String, fileOut: String, key: Int) {
        if (26 - key >= 0) encode(textIn, fileOut, 26 - key)
        else encode(textIn, fileOut, key - 26)
    }
}