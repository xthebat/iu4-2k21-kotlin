package com.github.galads.sem02

data class Parameter(val short: String, val long: String)

object Parser {
    @JvmStatic
    fun main(arg: Array<String>) {
        val arr = mutableListOf(Parameter("-m", "--mails"))
        val mails = Parameter("-m", "--mails")
    }
}