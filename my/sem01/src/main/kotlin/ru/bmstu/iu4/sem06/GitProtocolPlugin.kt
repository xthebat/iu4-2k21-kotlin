package ru.bmstu.iu4.sem06

import io.javalin.Javalin
import io.javalin.core.plugin.Plugin
import ru.bmstu.iu4.sem06.desc.commit.Commit
import java.time.temporal.ChronoUnit

class GitProtocolPlugin(val gitPath: String, val settings: Settings) : Plugin {
    data class Response(val field: String)

    lateinit var commits: List<Commit>

    override fun apply(app: Javalin) {
        app.routes {
            app.getSafe("test") {
                val param = requireNotNull(queryParam("name")) { "param should not be null" }
                json(Response(param))
            }

            app.getSafe("parse") {
                val text = git(gitPath)

                commits = text
                    .removePrefix("commit ")
                    .split("\n\ncommit ")
                    .map { string ->
                        Commit
                            .runCatching { fromString(string, settings.aliases) }
                            .onFailure { println("Can't parse commit string:\n$string") }
                            .getOrThrow()
                    }

                result("Repository successfully parsed: $gitPath")
            }

            app.getSafe("stat") {
                val name = requireNotNull(queryParam("name")) { "name should not be null" }

                val chrono = ChronoUnit.DAYS
                val startDate = commits.minOf { it.date }.toLocalDate()

                val result = commits
                    .filter { it.name == name }
                    .groupBy { chrono.between(startDate, it.date.toLocalDate()) }
                    .map { (units, groupedByDateCommits) ->
                        val totalChanges = groupedByDateCommits.sumOf { it.changes.size }
                        val countOfCommits = groupedByDateCommits.size

                        val date = startDate.plus(units, chrono)

                        StatisticsEntry(date.toDate(), totalChanges, countOfCommits)
                    }
                    .sortedBy { it.date }

                json(result)
            }

            app.getSafe("users") {
                json(commits.map { it.name }.toSet())
            }
        }
    }
}