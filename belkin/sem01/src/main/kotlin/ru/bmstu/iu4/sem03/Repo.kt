package ru.bmstu.iu4.sem03

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


object Repo {

    data class Parameter<T : Any>(
        val short: String,
        val long: String,
        val mandatory: Boolean = false,
        val convert: (String) -> T
    )

    fun Array<out Parameter<*>>.findParameter(shortOrLong: String) =
        find { shortOrLong == it.long || shortOrLong == it.short }

    fun Array<String>.findDuplicates(vararg parameters: Parameter<*>) =
        filter { parameters.findParameter(it) != null }
            .groupingBy { it }
            .eachCount()
            .filterValues { it > 1 }
            .keys


    fun <T> Collection<IndexedValue<T>>.findNeighbourWithoutGap() =
        zipWithNext().find { (prev, next) -> prev.index + 1 == next.index }?.first?.value

    fun Array<String>.parseArguments(vararg parameters: Parameter<*>): Map<Parameter<*>, Any> {
        // validate that we have no duplicated parameter keys
        val duplicates = findDuplicates(*parameters)
        require(duplicates.isEmpty()) { "Parameter $duplicates duplicated!" }

        return mapIndexed { index, argument ->
            val parameter = parameters.findParameter(argument)
            if (parameter != null) IndexedValue(index, parameter) else null
        }.filterNotNull()
            .apply {
                // validate that we have values for all parameters keys
                val noValueArgument = findNeighbourWithoutGap()
                require(noValueArgument == null) { "Parameter ${noValueArgument!!.long} has no value" }

                // validate that all mandatory parameters are set
                val foundParameters = map { it.value }
                val mandatoryParameters = parameters.filter { it.mandatory }
                val omittedMandatoryParameter = mandatoryParameters.find { it !in foundParameters }
                require(omittedMandatoryParameter == null) {
                    "Mandatory parameter ${omittedMandatoryParameter!!.long} not specified"
                }
            }.associate { (index, parameter) ->
                require(index + 1 < this.size) { "not value for parameter ${parameter.long}" }
                val value = this[index + 1]
                val actual = parameter.convert(value)
                parameter to actual
            }
    }

    fun <T : Any> Map<Parameter<*>, Any>.getParameter(parameter: Parameter<T>) =
        requireNotNull(this[parameter]) { "Parameter ${parameter.long} not found" } as T

    @JvmStatic
    fun main(args: Array<String>) {

        val directory = Parameter("-d", "--directory", true) { it }
        val count = Parameter("-m", "--max-depth", true) { it.toInt() }

        val parameters = args.parseArguments(directory, count)

        val countValue = parameters.getParameter(count)
        val directoryValue = parameters.getParameter(directory)


        val dir = Paths.get(directoryValue)
        val directories = mutableListOf<File>()

        Files.walk(dir, countValue)
            .filter { p: Path -> Files.isDirectory(p) }
            .forEach { p: Path -> directories.add(p.toFile()) }

        directories.forEach {
            it.listFiles().forEach {
                if (it.isDirectory) {
                    println("${it.length()} \t ${it.list().size} \t $it")
                } else {
                    println("${it.length()} \t $it")
                }
            }
        }

    }
}