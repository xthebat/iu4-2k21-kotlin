package ru.bmstu.iu4.sem03

import java.io.File

class DirectoryRecursiveIterator(val file: File, val depth: Int) : Iterator<File> {
    var currentFile: File? = null

    override fun hasNext(): Boolean {
        TODO("Not yet implemented")
    }

    override fun next(): File {
        TODO("Not yet implemented")
    }
}

fun File.traverse(depth: Int = -1) = Iterable { DirectoryRecursiveIterator(this@traverse, depth) }

// Not yet tested
fun File.depth(base: File) = absolutePath
    .removePrefix(base.absolutePath)
    .count { it == File.pathSeparatorChar }

fun main(array: Array<String>) {
    println(File("temp").listFiles().joinToString("\n") { "${it.absolutePath} ${it.isDirectory}" })

    val directory ="/home/user"
    val base = File(directory)

    base
        .traverse()
        .forEach { file ->
            println("%s %s".format(file.depth(base), file.absolutePath))
        }
}