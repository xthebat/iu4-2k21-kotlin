package ru.bmstu.iu4.sem06.desc.commit

import ru.bmstu.iu4.sem06.desc.changes.Change
import java.text.SimpleDateFormat
import java.util.*


data class Commit(
    val id: String,
    val author: String,
    val email: String,
    val date: Date,
    val isMerge: Boolean,
    val comment: String,
    val changes: List<Change>
//    val summary: String
) {
    companion object {
        private val formatter = SimpleDateFormat("E MMM dd HH:mm:ss yyyy z")

        fun fromHeader(
            id: String,
            header: Map<String, String>,
            comment: String,
            changes: List<Change>
        ): Commit {
            val isMerge = "Merge" in header
            val authorAndMail = header.getValue("Author")
            val dateString = header.getValue("Date")

            val email = authorAndMail.substringAfterLast("<").substringBefore(">")
            val author = authorAndMail.substringBeforeLast("<").trim()

            val date = formatter.parse(dateString)

            return Commit(id, author, email, date, isMerge, comment, changes)
        }

        fun fromString(string: String): Commit {
            val lines = string.lines()

            val id = lines.first()

            val header = lines
                .drop(1)
                .takeWhile { it.isNotBlank() }
                .associate {
                    it.substringBefore(":") to
                            it.substringAfter(":").trim()
                }

            val comment = lines
                .filter { it.startsWith("    ") }
                .joinToString("\n") { it.trim() }

            val changes = lines.filter {
                it.startsWith(" ") && !it.startsWith("    ") && "|" in it
            }.map {
                Change.fromString(it)
            }

//            val summary = if (changes.isNotEmpty()) lines.last() else null

            return fromHeader(id, header, comment, changes)
        }
    }
}