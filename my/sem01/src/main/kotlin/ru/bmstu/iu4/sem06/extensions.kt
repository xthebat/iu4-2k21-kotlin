@file:Suppress("NOTHING_TO_INLINE")

package ru.bmstu.iu4.sem06

inline fun String.splitTrim(delimiter: String) = split(delimiter).map { it.trim() }

inline fun String.substringBetween(after: String, before: String) = substringAfter(after).substringBefore(before)
