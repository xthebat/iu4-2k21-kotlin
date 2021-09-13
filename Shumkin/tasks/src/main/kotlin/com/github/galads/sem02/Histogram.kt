package com.github.galads.sem02

import java.io.File
import kotlin.random.Random

private fun countCharacters(str: String, chr:Char): Int {
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

private fun getEmptyIndex(arr:Array<Double?>):Int? {
    for ((id, elem) in arr.withIndex()) {
        if (elem == null)
            return id
    }
    return null
}

private fun probabilityGenerate(map: List<String>, assMap: MutableMap<Char, Int>) {
    var allChr = 0
    assMap.forEach { (k, v) -> allChr += v }

    val arrProb = arrayOfNulls<Double>(allChr)
    for (i in 0 until allChr) //replay allChr to anymore ((0..10).rand())
        arrProb[i] = Random.nextDouble()
    arrProb.sort()

//    val arrAssMap = arrayOfNulls<Double>(allChr)

    val arrAssMap = mutableMapOf<Char, Double>()
//    println("\tHISTOGRAM")
//    assMap.toList().forEachIndexed { index, element ->
//        val id = getEmptyIndex(arrAssMap)
//        if (id != null)
//            for (i in id until id + element.second)
//                if (arrAssMap[i] == null)
//                    arrAssMap[i] = element.second / allChr.toDouble()
////        println("id === $id")
//        println("Char: ${element.first} Prob: ${element.second / allChr.toDouble()}")
//    }
    println(arrAssMap.toList())






//    val outFile = File("input" + ".out").bufferedWriter()
//    println(allChr)
}

object Histogram {
    @JvmStatic
    fun main(arg: Array<String>) {
        val map = File("src/main/kotlin/com/github/galads/sem02/input")
            .readLines()
            .map {  it  }

        val assMap = mutableMapOf<Char, Int>()
        for (str in map) {
            for (chr in str)
                assMap[chr] = arrCountCharacters(map, chr)
        }
        probabilityGenerate(map, assMap)
    }
}