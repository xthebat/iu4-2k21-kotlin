@file:Suppress("NOTHING_TO_INLINE")

package ru.bmstu.iu4.sem06

import io.javalin.Javalin
import ru.bmstu.iu4.sem03.Parameter
import ru.bmstu.iu4.sem03.getParameter
import ru.bmstu.iu4.sem03.parseArguments
import ru.bmstu.iu4.sem06.desc.changes.Change
import ru.bmstu.iu4.sem06.desc.changes.TextChange
import ru.bmstu.iu4.sem06.desc.commit.Commit
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*


inline fun Collection<Change>.totalTextChanges() = filterIsInstance<TextChange>().sumOf { it.count }

private val unixTimeStart = LocalDate.of(1970, 1, 1)


fun Date.toLocalDate(): LocalDate = toInstant()
    .atZone(ZoneId.systemDefault())
    .toLocalDate()


data class StatisticsEntry(val date: Date, val changes: Int, val commits: Int)


fun main(args: Array<String>) {

    val settingsParam = Parameter("-s", "--settings", true) { it }
    val repositoryParam = Parameter("-r", "--repository", true) { it }
//    val portParam = Parameter("-p", "--port", true) { it.toInt() }

    val parameters = args.parseArguments(settingsParam, repositoryParam)

    val settingsPath = parameters.getParameter(settingsParam)
    val repositoryPath = parameters.getParameter(repositoryParam)
    val port = 8080

    val settings = settingsPath.toFile().fromJson<Settings>()

    val server = Javalin.create {
        it.registerPlugin(GitProtocolPlugin(repositoryPath, settings))
    }.apply {
        start(port)
    }



//    val millisInDay = 86400 * 1000



//    println(commitsByUser)

//    val changesByUser = commitsByUser
//        .mapValues { (_, userCommits) ->
//            userCommits
//                .flatMap { it.changes }
//                .filterIsInstance<TextChange>()
//                .sumOf { it.count }
//        }
//        .onEach { (user, changes) ->
//            println("$user -> $changes")
//        }

//    println(changesByUser)

//    .mapValues { (_, userCommits) ->
//        userCommits.sumOf { it.changes.totalTextChanges() }
//    }

//    commits.forEach { commit ->
//        val totalChanges = commit.changes.filterIsInstance<TextChange>().sumOf { it.count }
//        println("total=$totalChanges commit=$commit")
//    }
}