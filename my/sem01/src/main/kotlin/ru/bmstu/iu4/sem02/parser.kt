package ru.bmstu.iu4.sem02

data class Parameter<T>(val short: String, val long: String, val converter: (String) -> T)

fun <T> parameter(short: String, long: String, converter: (String) -> T): Parameter<T> {
    TODO("Not Implemented")
}

fun Array<String>.parseArguments(vararg parameters: Parameter<*>): Map<Parameter<*>, Any?> {
    TODO("Not Implemented")
}


// --mails we@gmail.com,all@gmail.com,any@gmail.com
fun main(args: Array<String>) {
    val mails = parameter("-m", "--mails") { it.split(",") }
    val parameters = args.parseArguments(mails)
    val mailsValue = parameters[mails] as List<String>
    mailsValue.forEach { println(it) }
}