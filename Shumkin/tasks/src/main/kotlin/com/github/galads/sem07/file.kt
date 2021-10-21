package com.github.galads.sem07

import java.io.File

inline fun String.toFile() = File(this)
