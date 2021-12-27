package ru.bmstu.iu4.sem05

import java.io.File

object FileWalker {
    private val CACHE_LIST = HashMap<File, List<File>>()
    private val CACHE_SIZE = HashMap<File, Long>()

    @JvmStatic
    fun main(args: Array<String>) {
        val file = File(args[0])
        file.listFileCached().sortedBy { it.totalSize() }.forEach {
            printCurrentLevel(0, it, true)
        }
    }

    private fun printCurrentLevel(level: Int, path: File, sorting: Boolean) {
        if (path.isFile) {
            printLevel(level, path.totalSize(), null, path.name)
            return
        }
        var files = path.listFileCached().toList()
        if (sorting) {
            files = files.sortedBy { it.totalSize() }
        }
        printLevel(level, path.totalSize(), files.size, path.name)
        files.forEach {
            printCurrentLevel(level + 1, it, sorting)
        }
    }

    private fun printLevel(level: Int, size: Long, count: Int?, name: String) {
        var padding = ""
        if (level > 0) {
            padding = "%${level}s".format(" ")
        }
        println("%10s %4s %s%-70s".format(size.toHumanReadableSize(), count ?: "", padding, name))
    }

    private fun File.totalSize(): Long {
        val sizeFromHash = CACHE_SIZE[this]
        if (sizeFromHash != null) {
            return sizeFromHash
        }
        val sizeFromFileSystem = if (isFile) {
            length()
        } else if (isDirectory) {
            listFileCached().sumOf { it.totalSize() }
        } else error("This is not a directory and not a file")
        CACHE_SIZE[this] = sizeFromFileSystem
        return sizeFromFileSystem
    }

    private const val BYTES_IN_KB = 1024L
    private const val BYTES_IN_MB = BYTES_IN_KB * 1024
    private const val BYTES_IN_GB = BYTES_IN_MB * 1024

    private fun Long.toHumanReadableSize(): String {
        return if (this < 0) {
            error("Size can't be less then zero")
        } else if (this < BYTES_IN_KB) {
            "${this}B"
        } else if (this < BYTES_IN_MB) {
            "${this / BYTES_IN_KB}Kb"
        } else if (this < BYTES_IN_GB) {
            "${this / BYTES_IN_MB}Mb"
        } else "${this / BYTES_IN_GB}Gb"
    }

    private fun File.listFileCached(): List<File> {
        val filesFromCache = CACHE_LIST[this]
        if (filesFromCache != null) {
            return filesFromCache
        }
        val filesFromFileSystem = listFiles()?.asList() ?: emptyList()
        CACHE_LIST[this] = filesFromFileSystem
        return filesFromFileSystem
    }
}
