package ru.bmstu.iu4.sem06

fun git(path: String) = shell("git", "--no-pager", "-C", path, "log", "--stat")
