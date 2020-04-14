package com.programmer74.sdl1

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.mongodb.repository.MongoRepository
import java.io.File

fun <T, E, ID> MongoRepository<T, ID>.mapToFile(
  file: File,
  mapper: ObjectMapper,
  dtoMapper: (T) -> E
) = this.findAll().toList().mapToFile(file, mapper, dtoMapper)

fun <T, E> List<T>.mapToFile(file: File, mapper: ObjectMapper, dtoMapper: (T) -> E) {
  val data = this.map { dtoMapper.invoke(it) }
  file.writeText(mapper.writeValueAsString(data))
}