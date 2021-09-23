package sem02

import java.io.File

object Program {
    @JvmStatic
    fun main(args: Array<String>) {
        val text = File("sample.txt").readText() // read all the text as one string (not by lines)
        println("Original text:\n$text")
        val charsMap = mutableMapOf<Char, Int>() // create map of extendable size
        text.forEach {      //for every element of text - symbol in the string created from file
            charsMap[it] = charsMap.getOrDefault(it, 0) + 1
            //getOrDefault: get the value corresponding to current it
            //if non -> set the corresponding value to 0 and add 1
            //else add 1 to the corresponding value
        }
//        println(charsMap)

//        val charsMap = mutableMapOf<Char, Int>()
//        val text = File("sample.txt").readText().forEach {
//            charsMap[it] = charsMap.getOrDefault(it, 0) + 1
//        }
//        println(charsMap.toList().sortedBy { (_, value) -> value}.toMap())

//        println("number of characters in file = ${text.length}")

        var set = text.toSet()
        var nOfIntervals = set.size
        var n = text.length
//        println("set = $set")
        var i = 0

        var intervals =  Array(nOfIntervals) {Array(2) {0} }
        var intermap = mutableMapOf<Int, Char>()
        set.forEach{
            intervals[i][1] = n
            intervals[i][0] = n - charsMap[it]!!
            n -= charsMap[it]!!
            intermap[i] = it
            i += 1
        }
//        println("intermap = $intermap")
//        println("intervals = ${intervals[10][1]}")
       println("Random text:")
        var rand = (1..text.length).random()
        text.forEach {
            for (k in 0 until i-1) {
                if (rand >= intervals[k][0] && rand < intervals[k][1]) {
                    print(intermap[k])
                }
            }
            rand = (1..text.length).random()
        }
    }
}
