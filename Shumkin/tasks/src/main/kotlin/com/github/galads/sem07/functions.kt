package com.github.galads.sem07

fun git(path: String) = shell("git", "--no-pager", "-C", path, "log", "--stat")