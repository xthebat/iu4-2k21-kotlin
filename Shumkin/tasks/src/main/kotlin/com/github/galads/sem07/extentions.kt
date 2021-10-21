@file:Suppress("NOTHING_TO_INLINE")

package com.github.galads.sem07

import com.github.galads.sem07.desc.commit.Commit

inline fun String.splitTrim(reg: String) = split(reg).map { it.trim() }

inline fun String.substringBetween(before: String, after: String) = substringAfter(before).substringBefore(after)
