package ru.bmstu.iu4

import java.io.File
import kotlin.random.Random

object Program {
    @JvmStatic
    fun main(args: Array<String>) {
        histogram("inputTextFotHistogram.txt", 20, "randomText.txt")
        val cipherEn = CaesarCipher("En")
        cipherEn.encrypt("textEn1.txt", "cipherEn1.txt", 3)
        cipherEn.decrypt("cipherEn1.txt", "decryptedTextEn1.txt",3)

        cipherEn.encrypt("textEn2.txt", "cipherEn2.txt", 28)
        cipherEn.decrypt("cipherEn2.txt", "decryptedTextEn2.txt",28)

        val cipherRu = CaesarCipher("Ru")
        cipherRu.encrypt("textRu1.txt", "cipherRu1.txt", 4)
        cipherRu.decrypt("cipherRu1.txt", "decryptedTextRu1.txt",4)

        cipherRu.encrypt("textRu2.txt", "cipherRu2.txt", 34)
        cipherRu.decrypt("cipherRu2.txt", "decryptedTextRu2.txt",34)
    }

    private fun histogram(inputFilename: String, strLength: Int, outputFilename: String) {
        var map = mutableMapOf<Char, Double>()
        val text = File(inputFilename).readText()
        text.forEach { char -> map[char] = map.getOrPut(char) { 0.0 } + 1 }
        map = map.toSortedMap()
        map.forEach { (key, value) -> map[key] = value / text.length }
        println(map)
        var addProbability = 0.0
        val probabilityMap = mutableMapOf<Char, Pair<Double, Double>>()
        map.forEach { (key, value) ->
            probabilityMap[key] = Pair(addProbability, value + addProbability)
            addProbability += value
        }
        var randomStr = ""
        for (j in 1..strLength) {
            val randomValue = Random.nextDouble()
            probabilityMap.forEach { (key, pair) ->
                if (randomValue >= pair.first && randomValue < pair.second)
                    randomStr += key
            }
        }
        val writer = File(outputFilename).bufferedWriter()
        writer.write(randomStr)
        writer.close()
    }


    public class CaesarCipher (language:String) {
        private var arrayAlphabet:CharArray = charArrayOf()
        private var mapAlphabet: Map<Char, Int> = mapOf()

        init {
            if (language == "En"){
                arrayAlphabet = charArrayOf(
                    'A',
                    'B',
                    'C',
                    'D',
                    'E',
                    'F',
                    'G',
                    'H',
                    'I',
                    'J',
                    'K',
                    'L',
                    'M',
                    'N',
                    'O',
                    'P',
                    'Q',
                    'R',
                    'S',
                    'T',
                    'U',
                    'V',
                    'W',
                    'X',
                    'Y',
                    'Z'
                )
                mapAlphabet = mapOf<Char, Int>(
                    'A' to 0,
                    'B' to 1,
                    'C' to 2,
                    'D' to 3,
                    'E' to 4,
                    'F' to 5,
                    'G' to 6,
                    'H' to 7,
                    'I' to 8,
                    'J' to 9,
                    'K' to 10,
                    'L' to 11,
                    'M' to 12,
                    'N' to 13,
                    'O' to 14,
                    'P' to 15,
                    'Q' to 16,
                    'R' to 17,
                    'S' to 18,
                    'T' to 19,
                    'U' to 20,
                    'V' to 21,
                    'W' to 22,
                    'X' to 23,
                    'Y' to 24,
                    'Z' to 25,
                )
            }
            else if (language == "Ru") {
                arrayAlphabet = charArrayOf(
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??',
                    '??'
                )
                mapAlphabet = mapOf<Char, Int>(
                    '??' to 0,
                    '??' to 1,
                    '??' to 2,
                    '??' to 3,
                    '??' to 4,
                    '??' to 5,
                    '??' to 6,
                    '??' to 7,
                    '??' to 8,
                    '??' to 9,
                    '??' to 10,
                    '??' to 11,
                    '??' to 12,
                    '??' to 13,
                    '??' to 14,
                    '??' to 15,
                    '??' to 16,
                    '??' to 17,
                    '??' to 18,
                    '??' to 19,
                    '??' to 20,
                    '??' to 21,
                    '??' to 22,
                    '??' to 23,
                    '??' to 24,
                    '??' to 25,
                    '??' to 26,
                    '??' to 27,
                    '??' to 28,
                    '??' to 29,
                    '??' to 30,
                    '??' to 31,
                )
            }
        }
        private val powerAlphabet = arrayAlphabet.size
        public fun encrypt(inputFilename: String, outputFilename: String, key: Int) {
            val inputText = File(inputFilename).readText()
            var outputText = ""
            inputText.forEach { char ->
                val newChar = if (char.isLowerCase()) {
                    char.uppercaseChar()
                } else char
                outputText += if (mapAlphabet.containsKey(newChar)) {
                    val newValue: Int = (mapAlphabet.getValue(newChar) + key) % powerAlphabet
                    if (char.isLowerCase()) {
                        arrayAlphabet[newValue].lowercase()
                    } else arrayAlphabet[newValue]
                } else char
            }
            val writer = File(outputFilename).bufferedWriter()
            writer.write(outputText)
            writer.close()
        }
        public fun decrypt(inputFilename: String, outputFilename: String, key: Int) {
            val inputText = File(inputFilename).readText()
            var outputText = ""
            inputText.forEach { char ->
                val newChar = if (char.isLowerCase()) {
                    char.uppercaseChar()
                } else char
                outputText += if (mapAlphabet.containsKey(newChar)) {
                    var newValue: Int = (mapAlphabet.getValue(newChar) - (key % powerAlphabet))
                    if (newValue < 0) newValue+= powerAlphabet
                    if (char.isLowerCase()) {
                        arrayAlphabet[newValue].lowercase()
                    } else arrayAlphabet[newValue]
                } else char
            }
            val writer = File(outputFilename).bufferedWriter()
            writer.write(outputText)
            writer.close()
        }
    }

}