@file:Suppress("NOTHING_TO_INLINE")

package ru.bmstu.iu4.sem06

import ru.bmstu.iu4.sem06.desc.changes.Change
import ru.bmstu.iu4.sem06.desc.changes.TextChange
import ru.bmstu.iu4.sem06.desc.commit.Commit

inline fun Collection<Change>.totalTextChanges() = filterIsInstance<TextChange>().sumOf { it.count }

fun main(args: Array<String>) {
    val jsonPath = args[0]
    val settings = jsonPath.toFile().fromJson<Settings>()
//    println(settings.path)

    val text = git(settings.path)
//    println(text)

    val commits = text
        .removePrefix("commit ")
        .split("\n\ncommit ")
        .map { string ->
            Commit
                .runCatching { fromString(string) }
                .onFailure { println("Can't parse commit string:\n$string") }
                .getOrThrow()
        }

    val commitsByUser = commits.groupBy { it.email }

//    println(commitsByUser)

    val changesByUser = commitsByUser
        .mapValues { (_, userCommits) ->
            userCommits
                .flatMap { it.changes }
                .totalTextChanges()
        }
        .onEach { (user, changes) ->
            println("$user -> $changes")
        }

//    println(changesByUser)

//    .mapValues { (_, userCommits) ->
//        userCommits.sumOf { it.changes.totalTextChanges() }
//    }

//    commits.forEach { commit ->
//        val totalChanges = commit.changes.filterIsInstance<TextChange>().sumOf { it.count }
//        println("total=$totalChanges commit=$commit")
//    }
}