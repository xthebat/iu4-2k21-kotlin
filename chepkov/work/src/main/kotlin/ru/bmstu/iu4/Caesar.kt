package ru.bmstu.iu4

import java.io.File

object Caesar {
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
        if (args[0] == "encode") {
            File(args[2]).writeText(encode(File(args[1]).readText(), args[3].toInt()))
            endMessage = "Encoding complete!"
        } else if (args[0] == "decode") {
            File(args[2]).writeText(decode(File(args[1]).readText(), args[3].toInt()))
            endMessage = "Decoding complete!"
        }
        print("$endMessage Check ${args[2]} to see result.")
    }
}
