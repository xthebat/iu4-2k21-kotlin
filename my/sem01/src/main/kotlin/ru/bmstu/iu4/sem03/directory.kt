package ru.bmstu.iu4.sem03

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.system.measureNanoTime
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

typealias Cache = MutableMap<File, Array<File>>

fun File.listFilesOrThrow(): Array<File> = listFiles() ?: error("File is not directory")

class DirectoryRecursiveIteratorTemplate(
    val file: File,
    val depth: Int,
    private val cache: Cache
) : Iterator<File> {
    private val internalStack = LinkedList<IndexedValue<Array<File>>>()
    private var currentDepth = 0
    private var currentFiles = arrayOf(file)
    private var index = 0

    private fun pushStack() {
        val item = IndexedValue(index, currentFiles)
        internalStack.push(item)
        currentDepth++
    }

    private fun popStack() {
        val item = internalStack.pop()
        currentFiles = item.value
        index = item.index
        currentDepth--
    }

    private fun canGoDeeper() = depth == -1 || currentDepth < depth

    override fun hasNext(): Boolean {
        if (index < currentFiles.size) {
            val file = currentFiles[index]
            if (file.isDirectory && canGoDeeper()) {
                pushStack()
                index = 0
                currentFiles = cache.getOrPut(file) { arrayOf(file) + file.listFilesOrThrow() }
            }
            return true
        } else {
            while (internalStack.isNotEmpty()) {
                popStack()

                if (++index < currentFiles.size) break
            }
            return index < currentFiles.size
        }
    }

    override fun next() = currentFiles[index++]
//        .also {
//            println(it)
//        }
}

fun File.traverse(cache: Cache = mutableMapOf(), depth: Int = -1) = Iterable {
    DirectoryRecursiveIteratorTemplate(this, depth, cache)
}

// Not yet tested
fun File.depth(base: File) = absolutePath
    .removePrefix(base.absolutePath)
    .count { it == File.separatorChar }


//fun File.totalSize(): Long = if (isDirectory) listFilesOrThrow().sumOf { it.totalSize() } else length()

fun File.totalSize(cache: Cache = mutableMapOf()): Long =
    traverse(cache)
        .filterNot { it.isDirectory }
        .sumOf { it.length() }

fun Long.humanReadableSize() = when {
    this < 0 -> error("Not a filesize")
    this < 1024 -> "${this} B"
    this < 1_048_576 -> "${this / 1024}KB"
    this < 1_073_741_824 -> "${this / 1024 / 1024}GB"
    else -> "${this / 1024 / 1024 / 1024}TB"
}

@OptIn(ExperimentalTime::class)
fun main(array: Array<String>) {
//    println(File("temp").listFiles().joinToString("\n") { "${it.absolutePath} ${it.isDirectory}" })

    val directory = "temp"
    val base = File(directory)

    val traverseCache = mutableMapOf<File, Array<File>>()

    val (_, warmupTime) = measureTimedValue { repeat(100000000) { base.traverse() } }
    val (_, noCacheTime) = measureTimedValue { repeat(100000000) { base.traverse() } }
    val (_, withCacheTime) = measureTimedValue { repeat(100000000) { base.traverse(traverseCache) } }

    println("warmupTime=$warmupTime noCacheTime=$noCacheTime withCacheTime=$withCacheTime")

    base
        .traverse(traverseCache)
        .sortedWith(
            compareBy<File> { if (it.isDirectory) it else it.parentFile }
                .thenBy { -it.totalSize(traverseCache) }
        )
        .forEach { file ->
            val size = file.totalSize(traverseCache)
            val hrf = size.humanReadableSize()
            val depth = file.depth(base)
            val path = file.absolutePath
                .removePrefix(base.absolutePath)
                .removePrefix("/")
//            val pad = if (depth != 0) "%%%ds".format(depth * 4).format(" ") else ""
            val pad = ""
            println("%s %10s %s %-70s".format(depth, hrf, pad, path))
        }
}