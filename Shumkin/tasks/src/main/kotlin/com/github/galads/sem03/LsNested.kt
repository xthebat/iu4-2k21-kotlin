package com.github.galads.sem03

import java.io.File
import java.util.*
import kotlin.system.exitProcess


////        treeWalk = file.walk()
////        treeWalk.forEach { it }
//        list = file.listFiles()
////        currentFile = null
//        next = (list as Array<out File>?)!!.iterator().hasNext()
//        println("$next<--------------------next")
//        when (currentFile) {
//            null -> currentFile = list!![0]
//            else -> currentFile = list!![currentIndex!!]
//        }
//        if ((list!!.indices.find { it == list!!.indexOf(currentFile) + 1 }) != -1) {
//            if (currentIndex != null)
//                currentIndex = currentIndex!!.inc()
//            else
//                currentIndex = 0
//        }
//        return when (currentFile) {
//            null -> false
//            else -> true
//        }


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
    fun forEachDepth(visit: (TreeNode<T>) -> Unit) {
        visit(this)
        child.forEach {
            it.forEachDepth(visit) //is not recursive maybe, recursive check without it
        }
    }
}

class TreeNodeFile(value: File) : TreeNode<File>(value) {
    var depth = 3
    var current: File? = null
    var currentFiles: Array<File>? = null
    fun forEachLvlOrder(visit: (TreeNode<File>) -> Unit) {
        println("----------")
        for (i in 0 until queue.count) {
            println(queue.dequeue())
        }
    }
}
/*    override fun hasNext(): Boolean {
        when {
            index < treeDirectory!!.size -> {
                index++
                return true
            }
            else -> return false
        }
    }

    override fun next(): File {
        return treeDirectory!![index - 1]
    }
    ///////////work
           fileTree.fill(dir.listFiles()!!)
        while (!fileTree.isEmpty) {
            println(fileTree.peek())
            val currentFile = fileTree.dequeue()
            if (currentFile!!.isDirectory && currentDepth >= 0) {
                currentDepth--
                fileTree.fill(currentFile.listFiles()!!)
            } else {
//                result.add(currentFile.absolutePath)
            }
            result.add(currentFile.absolutePath)
        }

    */

class DirectoryRecursiveIterator(val file: File, val depth: Int) : Iterator<File> {
    private val treeDirectory: Array<File>? = file.listFiles()
    private var currentArrDir: Array<File>? = null
    private var tree = TreeNodeFile(file).apply {
        for (i in 0 until treeDirectory!!.size) {
            add(TreeNodeFile(treeDirectory[i]))
        }
    }
    private var index = 0
    private var size = 0
    private var currentDepth = depth

    /////////////////
    private var fileTree = ArrayListQueue<File>()
    private val result = mutableListOf<File>()
    private var dir = file

    ///////////////////
    private var treeNods = TreeNode<File>(file)

    ////new test
    private var treeList = mutableListOf<Any>()
    override fun hasNext(): Boolean {
//        treeList.add(mutableListOf("2", mutableListOf("3", "4")))
//        treeList.add(mutableListOf("5"))
//        val  newSize = treeList.size
//        for (i in 0 until newSize) {
////            println(newSize)
//           println(treeList[i].toString())
//        }
//        exitProcess(4)
        if (result.isEmpty()) {
            fileTree.fill(dir.listFiles()!!)
            while (!fileTree.isEmpty) {
//            println(fileTree.peek())
                val currentFile = fileTree.dequeue()
                if (currentFile!!.isDirectory && currentFile.depth(file) < depth) {
                    fileTree.fill(currentFile.listFiles()!!)
                    for (i in 0 until currentFile.listFiles()!!.size) {
                        treeNods.add(TreeNode(currentFile.listFiles()!![i]))
                    }
                } else {
//                    currentDepth = depth
                }
                result.add(currentFile)

            }
            result.sort()
        }
//        result.forEach {
//            println(it)
//        }
//        println(file)
        size = result.size
//        treeNods.forEachDepth { println(it.value) }
//        exitProcess(1)
        when {
            index < size -> {
                index++
                return true
            }
            else -> return false
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

object LsNested {
    @JvmStatic
    fun main(arg: Array<String>) {
//        println(File("temp").listFiles().joinToString("\n") { "${it.absolutePath} ${it.isDirectory}" })

        val directory = "/home/nico/iu4-2k21-kotlin/test"
        val deptLvl = 3

//
//        val rootDir: File = File(directory)
//        val result: MutableList<String> = ArrayList()
//        val fileTree: PriorityQueue<File> = PriorityQueue<File>()
//
//        Collections.addAll(fileTree, *rootDir.listFiles())
////        println() //1lvl
////        exitProcess(3)
//        while (!fileTree.isEmpty()) {
//            val currentFile: File = fileTree.remove()
//            if (currentFile.isDirectory) {
//                Collections.addAll(fileTree, *currentFile.listFiles())
//            } else {
//                result.add(currentFile.absolutePath)
//            }
////            println(fileTree)
//        }
        val base = File(directory)
//exitProcess(2);
//        val queue = ArrayListQueue<String>().apply {
//            enqueue("first")
//            enqueue("second")
//            enqueue("three")
//            enqueue("four")
//        }
//        for (item in 0 until  queue.count) {
//            println(queue.dequeue())
//        }

//        val hot = TreeNode("hot")
//        val cold = TreeNode("cold")
//        val bev = TreeNode("beverages").apply {
//            for (i in 0..3) {
//                add(TreeNode("a$i"))
//            }
//        }
//        bev.forEachDepth({ println(it.value) })
//        println(bev.value)


        base
            .traverse(deptLvl)
            .forEach { file ->
                println(
                    "%10.10sK| %10.10s| %-10s".format(
                        (file.length() / 1024).toFloat(),
                        file.depth(base),
                        file.toString().removePrefix("$base")
                    )
                )
            }
    }
}