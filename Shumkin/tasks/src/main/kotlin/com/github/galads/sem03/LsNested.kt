package com.github.galads.sem03

import java.io.File

interface Queue<T> {
    fun enqueue(element: T): Boolean
    fun dequeue(): T?
    val count: Int

    val isEmpty: Boolean
        get() = count == 0

    fun peek(): T?
}

class ArrayListQueue<T> : Queue<T> {
    private val list = arrayListOf<T>()
    override fun enqueue(element: T): Boolean {
        list.add(element)
        return true
    }

    override fun dequeue(): T? = if (isEmpty) null else list.removeAt(0)

    override val isEmpty: Boolean
        get() = super.isEmpty

    override fun peek(): T? = list.getOrNull(0)

    override val count: Int
        get() = list.size

    fun fill(arr: Array<out T>): Boolean {
        arr.forEach {
            list.add(it)
        }
        return true
    }

    fun removeAll() {
        list.removeAll(list.toSet())
    }
}

open class TreeNode<T>(val value: T) {
    val child = mutableListOf<TreeNode<T>>()
    var queue = ArrayListQueue<T>()
    fun add(child: TreeNode<T>) = this@TreeNode.child.add(child)
    fun forEachDepthRecursive(visit: (TreeNode<T>) -> Unit) {
        visit(this)
        child.forEach {
            it.forEachDepthRecursive(visit)
        }
    }
}

class DirectoryRecursiveIterator(val file: File, val depth: Int) : Iterator<File> {
    private var index = 0
    private var size = 0

    private var fileTree = ArrayListQueue<File>()
    private val result = mutableListOf<File>()
    private var dir = file

    private var treeNods = TreeNode(file)

    override fun hasNext(): Boolean {
        if (result.isEmpty()) {
            fileTree.fill(dir.listFiles()!!)
            while (!fileTree.isEmpty) {
                val currentFile = fileTree.dequeue()
                if (currentFile!!.isDirectory && currentFile.depth(file) < depth) {
                    fileTree.fill(currentFile.listFiles()!!)
                    for (i in 0 until currentFile.listFiles()!!.size) {
                        treeNods.add(TreeNode(currentFile.listFiles()!![i]))
                    }
                }
                result.add(currentFile)
            }
            result.sort()
        }
        size = result.size
        /*
       var list:List<String> = result.map { it.toString() }
       list = list.sorted()
        var list2 = list.sortedWith(Comparator.comparing(f1, f2)  {
            if (it == "/")
                it.substring(it.lastIndexOf("/") + 1)
            else
                it.toSortedSet().toString()
        })
        */
        return when {
            index < size -> {
                index++
                true
            }
            else -> false
        }
    }

    override fun next(): File {
        return result[index - 1]
    }
}

fun File.traverse(depth: Int = -1) = Iterable { DirectoryRecursiveIterator(this@traverse, depth) }

fun File.depth(base: File) = absolutePath
    .removePrefix(base.absolutePath)
    .count { value -> value == File.separatorChar }

fun File.depthLast(base: File): File = File(absolutePath.removePrefix(base.toString()))

fun File.folderSize(dir: File): Float {
    var len = 0.0F
    if (dir.listFiles() != null && dir.listFiles().isNotEmpty()) {
        for (file in dir?.listFiles()) {
            if (dir?.listFiles().size == 1)
                len += dir.length()
            if (file.isFile)
                len += file.length()
            else
                len += folderSize(file)
        }
    } else {
        len += dir.length()
    }
    return len
}

fun File.countFilesInDir(): Int {
    val files = this.listFiles()

    return files?.size ?: 0
}


/*parameters*/

typealias ConvertParam<T> = (String) -> T

data class Parameter<T : Any>(val short: String, val full: String, val convert: ConvertParam<T>)

fun Array<String>.parseArguments(vararg parameter: Parameter<*>): Map<Parameter<*>, *> {
    return mapIndexed { index, name ->
        val param = parameter.find { name == it.full || name == it.short }
        if (param != null) index to param else null
    }
        .filterNotNull()
        .associate { (index, parameter) ->
            val value = this[index + 1]
            parameter to parameter.convert(value)
        }
}

/*parameters*/

object LsNested {
    @JvmStatic
    fun main(arg: Array<String>) {

        val dir = Parameter("-d", "--directoryPath") { it }
        val depth = Parameter("-m", "--maxDepth") { it.toInt() }
        val parameters = arg.parseArguments(dir, depth)
        val base = File(parameters[dir] as String)

        base
            .traverse(parameters[depth] as Int)
            .forEach { file ->
                println(
                    "%15.3fK| %10.10s| %s".format(
                        (file.folderSize(file) / 1024),
                        if (file.isDirectory) file.countFilesInDir() else "file",
                        (file.toString()
                            .removePrefix("${file.parentFile}"))
                            .padStart(
                                file
                                    .toString()
                                    .removePrefix("${file.parentFile}")
                                    .length + file.depth(base) - 1, '\t'
                            )
                    )
                )
            }
    }
}