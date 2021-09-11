package ru.bmstu.iu4.sem02

data class Parameter<T>(val short: String, val long: String, val converter: (String) -> T)

fun <T> parameter(short: String, long: String, converter: (String) -> T): Parameter<T> {
    TODO("Not Implemented")
}

fun Array<String>.parseArguments(vararg parameters: Parameter<*>): Map<Parameter<*>, Any?> {
    TODO("Not Implemented")
}


// --mails we@gmail.com,all@gmail.com,any@gmail.com -i 1
fun main(args: Array<String>) {
    val mails = parameter("-m", "--mails") { it.split(",") }
    val interval = parameter("-i", "--interval") { it.toInt() }

    val parameters = args.parseArguments(mails, interval)

    val mailsValue = parameters[mails] as List<String>
    val intervalValue = parameters[interval] as Int
    mailsValue.forEach { println(it) }
    println("Spam interval is $intervalValue")
}