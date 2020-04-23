package com.programmer74.sdl1.dataretrieve

import com.fasterxml.jackson.module.kotlin.readValue
import com.programmer74.sdl1.dtos.*
import org.springframework.stereotype.Service
import java.io.File

@Service
class MysqlDumpedDataRetriever {

  private val srcPath = File("/home/hotaro/st_and_db_labs_dumps/mysql")

  fun getMysqlPersons(): List<PersonDtoFromMysql> {
    val content = File(srcPath, "1__persons.json").readText()
    return mapper.readValue(content)
  }

  fun getMysqlConferences(): List<ConferenceDto> {
    val content = File(srcPath, "2__conferences.json").readText()
    return mapper.readValue(content)
  }

  fun getMysqlPublications(): List<PublicationDto> {
    val content = File(srcPath, "3__publications.json").readText()
    return mapper.readValue(content)
  }

  fun getMysqlProjects(): List<ProjectDto> {
    val content = File(srcPath, "4__projects.json").readText()
    return mapper.readValue(content)
  }

  fun getMysqlBooksTaken(): List<BookTakenDto> {
    val content = File(srcPath, "5__books_taken.json").readText()
    return mapper.readValue(content)
  }
}