package com.github.galads.sem03

import java.io.File

class DirectoryRecursiveIterator(val file: File, val depth: Int) : Iterator<File> {
    companion object {
        private lateinit var treeWalk: FileTreeWalk
        var currentFile: File? = null
        var list: Array<File>? = null
        var currentIndex: Int? = null
    }
    var next: Boolean? = null
    override fun hasNext(): Boolean {
        treeWalk = file.walk()
        treeWalk.forEach { it }
        list = file.listFiles()
        currentFile = null
        next = (list as Array<out File>?)!!.iterator().hasNext()
        when (currentFile) {
            null -> currentFile = list!![0]
            else -> currentFile = list!!.find { it == currentFile }
        }
        if ((list!!.indices.find { it == list!!.indexOf(currentFile) + 1 }) != -1) {
            if (currentIndex != null)
                currentIndex = currentIndex!!.inc()
            else
                currentIndex = 0
        }
        return when (currentFile) {
            null -> false
            else -> true
        }
    }

    override fun next(): File {
//        TODO("Not yet implemented")
        return File(currentFile?.absolutePath)
    }
}

fun File.traverse(depth: Int = -1) = Iterable { DirectoryRecursiveIterator(this@traverse, depth) }

// Not yet tested
fun File.depth(base: File) = absolutePath
    .removePrefix(base.absolutePath)
    .count { value -> value == '/' }

object LsNested {
    @JvmStatic
    fun main(arg: Array<String>) {
//        println(File("temp").listFiles().joinToString("\n") { "${it.absolutePath} ${it.isDirectory}" })

        val directory = "/home/nico"
        val deptLvl = 3

        val base = File(directory)
        val first = base.depth(base)
        println("============>$first")

        base
            .traverse(deptLvl)
            .forEach { file ->
                println("%s %s".format(file.depth(base), file.path))
//                exitProcess(1)
            }
    }
}