package com.programmer74.sdl1.datadump.oracle

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.programmer74.sdl1.datadump.mapToFile
import com.programmer74.sdl1.dtos.AssessmentDtoFromOracle
import com.programmer74.sdl1.dtos.LessonEntryDto
import com.programmer74.sdl1.dtos.PersonDtoFromOracle
import com.programmer74.sdl1.dtos.StudyGroupDto
import com.programmer74.sdl1.oracle.entities.Assessment
import com.programmer74.sdl1.oracle.entities.LessonEntry
import com.programmer74.sdl1.oracle.entities.Person
import com.programmer74.sdl1.oracle.entities.StudyGroup
import com.programmer74.sdl1.oracle.repositories.AssessmentRepository
import com.programmer74.sdl1.oracle.repositories.LessonEntryRepository
import com.programmer74.sdl1.oracle.repositories.PersonRepository
import com.programmer74.sdl1.oracle.repositories.StudyGroupRepository
import mu.KLogging
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.io.File
import javax.annotation.PostConstruct

@Profile("oracle")
@Service
class OracleDummyDataDumper(
  private val personRepository: PersonRepository,
  private val studyGroupRepository: StudyGroupRepository,
  private val lessonEntryRepository: LessonEntryRepository,
  private val assessmentRepository: AssessmentRepository
) {

  private val destPath = File("/home/hotaro/st_and_db_labs_dumps/oracle")

  @PostConstruct
  fun startGenerator() {
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
    lessonEntryRepository.mapToFile(File(destPath, "2__lesson_entries.json"), mapper) { it.toDto() }
    studyGroupRepository.mapToFile(File(destPath, "3__study_groups.json"), mapper) { it.toDto() }
    assessmentRepository.mapToFile(File(destPath, "4__assessments.json"), mapper) { it.toDto() }

    logger.warn { "all OK" }
  }

  private fun Person.toDto() = PersonDtoFromOracle(
      id!!,
      sid,
      name,
      birthDate.toEpochMilli(),
      birthPlace,
      faculty,
      position,
      isContractStudent,
      contractFrom.toEpochMilli(),
      contractTo.toEpochMilli())

  private fun LessonEntry.toDto() = LessonEntryDto(
      id!!,
      name,
      teacher.id!!,
      weekday,
      hour,
      minute,
      room)

  private fun StudyGroup.toDto() = StudyGroupDto(
      id!!,
      name,
      studyForm,
      school,
      speciality,
      qualification,
      studyYear,
      lessons.map { it.id!! }.toMutableList(),
      participants.map { it.id!! }.toMutableList())

  private fun Assessment.toDto() = AssessmentDtoFromOracle(
      id!!,
      name,
      mark,
      markLetter,
      achievedAt.toEpochMilli(),
      achievedBy.id!!,
      disciplineName)

  companion object : KLogging()
}