package com.github.galads.sem02

import java.io.File
import kotlin.math.roundToInt

private fun countCharacters(str: String, chr: Char): Int {
    var cnt = 0

    for (i in str)
        if (i == chr)
            cnt++
    return cnt
}

private fun arrCountCharacters(arrStr: List<String>, chr: Char): Int {
    var cnt = 0

    for (str in arrStr)
        cnt += countCharacters(str, chr)
    return cnt
}

private fun getEmptyIndexArr(arr: Array<Pair<Char, Double>?>): Int? {
    for ((id, elem) in arr.withIndex())
        if (elem == null)
            return id
    return null
}

private fun writeListToFile(arr: List<Char>, name: String = "${System.getProperty("user.dir")}/file.out") {
    File("src/main/kotlin/com/github/galads/sem02/file.out").printWriter().use { out ->
        arr.forEach {
            out.print(it)
        }
    }
}

private fun generateSymbol(arr: Array<Pair<Char, Double>?>, size: Int) {
    val randomSize = (size..size * (1..10).random()).random()

    val chrList = mutableListOf<Char>()
    var count: Int
    for (i in arr.indices) {
        count = (randomSize * arr[i]!!.second).roundToInt()
        (0 until count).forEach { _ ->
            chrList.add(arr[i]!!.first)
        }
    }
    chrList.shuffle()
    writeListToFile(chrList)
}

private fun probabilityGenerate(assMap: MutableMap<Char, Int>) {
    var allChr = 0
    assMap.forEach { (_, v) -> allChr += v }

    val arrAssMap: Array<Pair<Char, Double>?> = arrayOfNulls(assMap.size)
    println("\tHISTOGRAM")
    assMap.toList().forEachIndexed { _, element ->
        val id = getEmptyIndexArr(arrAssMap)
        if (id != null)
            arrAssMap[id] = Pair(element.first, element.second / allChr.toDouble())
        println("Char: ${element.first} Prob: ${element.second / allChr.toDouble()}")
    }
    generateSymbol(arrAssMap, allChr)
}

object Histogram {
    @JvmStatic
    fun main(arg: Array<String>) {
        if (arg.size != 1) {
            println("Error: not or more argument")
            return
        }
        val map = File(arg[0])
            .readLines()
            .map { it }

        val assMap = mutableMapOf<Char, Int>()
        for (str in map) {
            for (chr in str)
                assMap[chr] = arrCountCharacters(map, chr)
        }
        probabilityGenerate(assMap)
    }
}