package ru.bmstu.iu4.sem02

import java.io.File

fun main(args: Array<String>) {
    val mails = parameter("-m", "--mails") { it.split(",") }

    val javaVersion = System.getProperty("java.version")
    println(javaVersion)

    val parameters = args
        .associate {
            val (name, value) = it.split("=")
            name to value
        }

//    val input = requireNotNull(parameters["input"]) { "parameter 'input' is required" }
    val input = parameters["input"] ?: throw IllegalArgumentException("....")
    val dict = requireNotNull(parameters["dict"]) { "parameter 'dict' is required" }

    val subs = File(dict)
        .readLines()
        .associate {
            val (name, value) = it.split(":")
            name to value
        }

    File(input)
        .readLines()
        .associate {
            val (name, status) = it.split(":")
            name to status
        }.filterValues {
            it == "USED"
        }.map { (name, _) ->
            subs[name] ?: name
        }.forEach { println(it) }
}

