package com.programmer74.sdl1.datadump.mysql

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.programmer74.sdl1.datadump.mapToFile
import com.programmer74.sdl1.dtos.*
import com.programmer74.sdl1.mysql.entities.*
import com.programmer74.sdl1.mysql.repositories.*
import mu.KLogging
import org.springframework.context.annotation.Profile
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import java.io.File
import javax.annotation.PostConstruct

@Suppress("SameParameterValue")
@Profile("mysql")
@Service
class MysqlDummyDataDumper(
  private val personRepository: PersonRepository,
  private val conferenceRepository: ConferenceRepository,
  private val publicationRepository: PublicationRepository,
  private val projectRepository: ProjectRepository,
  private val bookTakenRepository: BookTakenRepository
) {

  private val destPath = File("/home/hotaro/st_and_db_labs_dumps/mysql")

  @PostConstruct
  fun startDumper() {
    if (personRepository.findAll().toList().isEmpty()) {
      logger.error { "Empty repository, cannot proceed" }
    } else {
      dumpAll()
    }
  }

  private fun dumpAll() {
    destPath.listFiles()?.forEach { it.delete() }
    destPath.mkdirs()
    val mapper = ObjectMapper().registerModule(KotlinModule())

    personRepository.mapToFile(File(destPath, "1__persons.json"), mapper) { it.toDto() }
    conferenceRepository.mapToFile(File(destPath, "2__conferences.json"), mapper) { it.toDto() }
    publicationRepository.mapToFile(File(destPath, "3__publications.json"), mapper) { it.toDto() }
    projectRepository.mapToFile(File(destPath, "4__projects.json"), mapper) { it.toDto() }
    bookTakenRepository.mapToFile(File(destPath, "5__books_taken.json"), mapper) { it.toDto() }

    logger.warn { "all OK" }
  }

  fun Person.toDto() = PersonDtoFromMysql(id!!, name, position)
  fun Conference.toDto() = ConferenceDto(id!!, name, place, date, participants.map { it.id!! })
  fun Publication.toDto() = PublicationDto(this.id!!,
      name,
      type,
      language,
      source,
      pages,
      sourceType,
      quoteIndex,
      date,
      authors.map { it.id!! })

  fun Project.toDto() = ProjectDto(id!!, name, dateFrom, dateTo, workers.map { it.id!! })
  fun BookTaken.toDto() = BookTakenDto(id!!, bookName, takenBy.id!!, takenAt, returnedAt)

  companion object : KLogging()
}