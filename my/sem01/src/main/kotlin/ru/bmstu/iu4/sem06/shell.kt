package ru.bmstu.iu4.sem06

fun shell(vararg args: String): String {
    val process = Runtime.getRuntime().exec(args)
    val text = process.inputStream.readAllBytes()
    return text.toString(Charsets.UTF_8)
}