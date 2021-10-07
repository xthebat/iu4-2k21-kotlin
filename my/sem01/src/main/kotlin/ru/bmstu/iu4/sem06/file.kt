package ru.bmstu.iu4.sem06

import java.io.File

inline fun String.toFile() = File(this)