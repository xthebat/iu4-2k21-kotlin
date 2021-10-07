package ru.bmstu.iu4.sem06

import java.util.*

data class Settings(val path: String, val authors: List<String>)

data class Change(val path: String, val count: Int, val line: String, val isBinary: Boolean)

data class Commits(
    val id: String,
    val author: String,
    val email: String,
    val date: String,  // Date
    val isMerge: Boolean,
    val comment: String,
    val changes: List<String>,   // List<Change>
//    val summary: String
)

fun git(path: String) = shell("git", "--no-pager", "-C", path, "log", "--stat")


fun main(args: Array<String>) {
    val jsonPath = args[0]
    val settings = jsonPath.toFile().fromJson<Settings>()
    println(settings.path)

    val text = git(settings.path)
    println(text)

    val commits = text
        .removePrefix("commit ")
        .split("\n\ncommit ")
        .map { string ->
            val lines = string.lines()

            val id = lines.take(1)

            val header = lines
                .drop(1)
                .takeWhile { it.isNotBlank() }
                .associate {
                    val (key, value) = it.split(":")
                    key to value.trim()
                }

            val comment = lines
                .filter { it.startsWith("    ") }
                .joinToString("\n") { it.trim() }

            val changes = lines.filter {
                it.startsWith(" ") && !it.startsWith("    ") && "|" in it
            }

            val summary = if (changes.isNotEmpty()) lines.last() else null

            println(header)
        }

    println(commits)
}