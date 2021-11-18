@file:Suppress("NOTHING_TO_INLINE")

package com.github.galads.sem07

import com.github.galads.sem07.desc.changes.BinaryChange
import com.github.galads.sem07.desc.changes.Change
import com.github.galads.sem07.desc.changes.RenameOrAddChange
import com.github.galads.sem07.desc.changes.TextChange
import com.github.galads.sem07.desc.commit.Commit
import java.text.SimpleDateFormat
import java.util.*


data class Settings(val path: String)

inline fun Collection<Change>.totalTextChanges() = filterIsInstance<TextChange>().sumOf { it.count }

inline fun Collection<Change>.totalBinaryChanges() =
    filterIsInstance<BinaryChange>().filter { it.size != -1L }.sumOf { it.size }

inline fun Collection<Commit>.totalIsMerge() = filter { it.isMerge }.size


fun printByteByUsers(commitsByUser: Map<String, List<Commit>>) {
    val byteByUsers = commitsByUser
        .mapValues { (_, userCommits) ->
            userCommits
                .sumOf {
                    it.changes.totalBinaryChanges()
                }
        }.onEach { (user, changes) ->
            println("$user -> $changes bin")
        }
}

fun printMergesByUser(commitsByUser: Map<String, List<Commit>>) {
    val mergesByUser = commitsByUser
        .mapValues { (_, userCommits) ->
            userCommits.totalIsMerge()
        }.onEach { (user, changes) ->
            println("merge: $user -> $changes")
        }
}

fun printRenameOrAddUserChanges(commitsByUser: Map<String, List<Commit>>) {
    val renameOrAddUser = commitsByUser
        .mapValues { (user, userCommits) ->
            userCommits
                .flatMap { it.changes }
                .filterIsInstance<RenameOrAddChange>()
                .onEach {
                    println("$user -> $it")
                }
        }
}

fun countChangesAfterAndBefore(user: String, commits: List<Commit>?, setDate: Date): Pair<Int, Int> {
    if (commits != null) {
        val before = commits.map {
            it.date.before(setDate)
        }.filter { it }.size
        val after = commits.map {
            it.date.after(setDate)
        }.filter { it }.size

        println("$user, $setDate, before: $before, after: $after")
        return before to after
    }
    return -1 to -1
}

fun main(args: Array<String>) {
    val jsonPath = args[0]
    val settings = jsonPath.toFile().fromJson<Settings>()

    val text = git(settings.path)
    val commits = text
        .removePrefix("commit ")
        .split("\n\ncommit")
        .map { string ->
            Commit
                .runCatching { fromString(string) }
                .onFailure { println("Can't parse commit string:\n$string") }
                .getOrThrow()
        }

    val commitsByUser = commits.groupBy { it.email }

    val changesByUser = commitsByUser
        .mapValues { (_, userCommits) ->
            userCommits
                .flatMap { it.changes }
                .totalTextChanges()
        }.onEach { (user, changes) ->
//            println("$user -> $changes")
        }
    printByteByUsers(commitsByUser)
    printMergesByUser(commitsByUser)
    printRenameOrAddUserChanges(commitsByUser)

    val user = "yaitskaya-helen@yandex.ru"
    val dateString = "Thu Sep 15 18:54:44 2021 +0300"
    val commitsForOneUser = commitsByUser.getValue(user)
    val formatter = SimpleDateFormat("E MMM dd HH:mm:ss yyyy z", Locale.ENGLISH)
    val date = formatter.parse(dateString)

    countChangesAfterAndBefore(user, commitsForOneUser, date)

//    println(renameOrAddUser)
}