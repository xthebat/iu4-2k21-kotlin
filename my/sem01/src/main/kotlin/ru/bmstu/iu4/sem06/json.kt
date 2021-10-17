package ru.bmstu.iu4.sem06

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.io.InputStream

val mappers = List(16) { ObjectMapper().registerModule(KotlinModule()) }

inline fun <reified T> InputStream.fromJson(mapper: ObjectMapper = mappers.random()) = mapper.readValue<T>(this)

inline fun <reified T> File.fromJson(mapper: ObjectMapper = mappers.random()) = mapper.readValue<T>(this)