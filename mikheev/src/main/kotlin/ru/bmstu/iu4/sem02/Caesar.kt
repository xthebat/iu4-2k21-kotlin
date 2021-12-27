package ru.bmstu.iu4.sem02

import java.io.File

object Caesar {
    private val upperAlphabet = ('A'..'Z').toMutableList()
    private val lowerAlphabet = ('a'..'z').toMutableList()

    @JvmStatic
    fun main(args: Array<String>) {
        val action = args[0]
        val inputFile = File(args[1])
        val outputFile = File(args[2])
        val encryptionKey = args[3].toInt()

        val output = if (action == "encode") {
            encode(inputFile.readText(), encryptionKey)
        } else if (action == "decode") {
            decode(inputFile.readText(), encryptionKey)
        } else error("Wrong arguments")

        outputFile.writeText(output)
    }

    private fun encode(input: String, key: Int): String {
        return input.map {
            when (it) {
                in upperAlphabet -> getChar(upperAlphabet, it, key)
                in lowerAlphabet -> getChar(lowerAlphabet, it, key)
                else -> it
            }
        }.toCharArray().joinToString("")
    }

    private fun decode(input: String, key: Int): String {
        return input.map {
            if (it in upperAlphabet) {
                getChar(upperAlphabet, it, -key)
            } else if (it in lowerAlphabet) {
                getChar(lowerAlphabet, it, -key)
            } else it
        }.toCharArray().joinToString("")
    }

    private fun getChar(alphabet: List<Char>, symbol: Char, key: Int): Char {
        val index = alphabet.indexOf(symbol)
        var newIndex = index + key
        if (newIndex >= alphabet.size) {
            newIndex = newIndex.mod(alphabet.size)
        } else if (newIndex < 0) {
            newIndex += alphabet.size
        }
        return alphabet[newIndex]
    }
}

