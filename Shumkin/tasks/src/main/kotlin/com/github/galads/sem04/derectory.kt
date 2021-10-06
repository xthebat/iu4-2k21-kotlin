package com.github.galads.sem04


import java.io.File
import java.util.*

typealias Cache = MutableMap<File, MutableList<File>?>

fun File.listFilesOrThrow(): Array<File> = listFiles() ?: error("File is not directory")

class DirectoryRecursiveIteratorTemplate(
    var file: File,
    var depth: Int,
    private val cache: Cache?
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
            if (cache!!.containsKey(currentFiles[index].parentFile) && !(currentFiles[index].isDirectory)) {
                file = cache[currentFiles[index].parentFile]!![index]
                return true
            }
            var file = currentFiles[index]
            while (file.isDirectory && canGoDeeper()) {
                if (cache.containsKey(file)) {
                    currentFiles = cache[file]!!.toTypedArray()
                    index = 0
                    file = currentFiles[index]
                } else {
                    if (currentFiles.isNotEmpty())
                        pushStack()
                    index = 0
                    currentFiles = file.listFilesOrThrow()
                    if (currentFiles.isEmpty()) {
                        popStack()
                        break
                    } else
                        file = currentFiles[index]
                }
            }
            return true
        } else {
            while (internalStack.isNotEmpty()) {
                popStack()
                if (index < currentFiles.size) break
            }
            return index < currentFiles.size
        }
    }

    override fun next(): File {
        if (!(cache!!.containsKey(currentFiles[index].parentFile)))
            cache[currentFiles[index].parentFile] = currentFiles[index].parentFile.listFiles()!!.toMutableList()
        return currentFiles[index++]
    }
}

fun File.traverse(cache: Cache?, depth: Int = -1) = Iterable {
    DirectoryRecursiveIteratorTemplate(this, depth, cache)
}

// Not yet tested
fun File.depth(base: File) = absolutePath
    .removePrefix(base.absolutePath)
    .count { it == File.separatorChar }


fun File.totalSize(): Long = if (isDirectory) listFilesOrThrow().sumOf { it.totalSize() } else length()

fun File.totalSize2(cache: Cache = mutableMapOf()): Long =
    traverse(cache)
        .filterNot {
            it.isDirectory
        }
        .sumOf {
            it.length()
        }

fun Long.humanReadableSize() = when {
    this < 0 -> error("Not a filesize")
    this < 1024 -> "${this} B"
    this < 1_048_576 -> "${this / 1024}KB"
    this < 1_073_741_824 -> "${this / 1024 / 1024}MB"
    else -> "${this / 1024 / 1024 / 1024}GB"
}

fun main(array: Array<String>) {

    val directory = "/home/nico/nwetest"
    val base = File(directory)

    val sizes = mutableMapOf<File, Long>()

    val traverseCache = mutableMapOf<File, MutableList<File>?>()

    base
        .traverse(traverseCache, 3)
//        .filter { it.depth(base) < 2 }
//        .filter { it.extension == "own" }
//        .filter { it.depth(base) != 0 }
//        .sortedBy { it }
        .map {
            it to it.totalSize2(traverseCache)
        }
        .sortedBy { it.first }
//        .forEach { println(it.toString() + it.totalSize2(cache = ) ) }
//        .sortedByDescending { (file, size) -> file.isDirectory }
//        .sortedByDescending { it.second }
//        .sortedByDescending { (file, size) -> size }
//        .sortedByDescending { it.second }
//        .sortedBy { it.second }
//        .find { it.nameWithoutExtension == "ceaser_input" }
/////////////////////////////////////////////////////////
        .sortedWith(compareBy<Pair<File, Long>> { it.first }.thenByDescending { it.second })
        .forEach { (file, size) ->
            val hrf = size.humanReadableSize()
            val depth = file.depth(base)
            val path = file.absolutePath
                .removePrefix(base.absolutePath)
                .removePrefix("/")
            val pad = if (depth != 0) "%%%ds".format(depth * 4).format(" ") else ""
            println("%s %10s %s %s".format(depth, hrf, pad, path))
        }
//    println(traverseCache)
}