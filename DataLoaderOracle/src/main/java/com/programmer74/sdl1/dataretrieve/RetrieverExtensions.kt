package com.programmer74.sdl1.dataretrieve

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

val mapper = ObjectMapper().registerModule(KotlinModule())

fun <T> File.mapToList(): List<T> {
  val content = this.readText()
  val res: List<T> = mapper.readValue(content)
  return res
}