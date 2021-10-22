@file:Suppress("NOTHING_TO_INLINE")

package ru.bmstu.iu4.sem06

import io.javalin.Javalin
import io.javalin.http.Context
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


inline fun String.splitTrim(delimiter: String) = split(delimiter).map { it.trim() }

inline fun String.substringBetween(after: String, before: String) = substringAfter(after).substringBefore(before)

inline fun Javalin.getSafe(path: String, crossinline action: Context.() -> Unit) {
    get(path) { context ->
        runCatching {
            action(context)
        }.getOrElse { error ->
            context.status(500)
            context.result(error.toString())
        }
    }
}

inline fun Javalin.postSafe(path: String, crossinline action: Context.() -> Unit) {
    post(path) { context ->
        runCatching {
            action(context)
        }.getOrElse { error ->
            context.status(500)
            context.result(error.toString())
        }
    }
}

fun LocalDate.toDate(): Date = Date.from(
    atStartOfDay()
        .atZone(ZoneId.systemDefault())
        .toInstant()
)