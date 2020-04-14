package com.programmer74.sdl1.datadump.postgre

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.programmer74.sdl1.datadump.mapToFile
import com.programmer74.sdl1.dtos.AssessmentDtoFromPostgre
import com.programmer74.sdl1.dtos.DisciplineDto
import com.programmer74.sdl1.postgre.entities.Assessment
import com.programmer74.sdl1.postgre.entities.Discipline
import com.programmer74.sdl1.postgre.repositories.AssessmentRepository
import com.programmer74.sdl1.postgre.repositories.DisciplineRepository
import mu.KLogging
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.io.File
import javax.annotation.PostConstruct

@Profile("postgre")
@Service
class PostgreDummyDataDumper(
  private val disciplineRepository: DisciplineRepository,
  private val assessmentRepository: AssessmentRepository
) {

  private val destPath = File("/home/hotaro/st_and_db_labs_dumps/postgre")

  @PostConstruct
  fun startDumper() {
    if (disciplineRepository.findAll().toList().isEmpty()) {
      logger.error { "Empty repository, cannot proceed" }
    } else {
      dumpAll()
    }
  }

  private fun dumpAll() {
    destPath.listFiles()?.forEach { it.delete() }
    destPath.mkdirs()
    val mapper = ObjectMapper().registerModule(KotlinModule())

    disciplineRepository.mapToFile(File(destPath, "1__disciplines.json"), mapper) { it.toDto() }
    assessmentRepository.mapToFile(File(destPath, "2__assessments.json"), mapper) { it.toDto() }

    logger.warn { "all OK" }
  }

  fun Discipline.toDto() = DisciplineDto(
      id!!,
      universityName,
      studyStandard,
      disciplineName,
      studyForm,
      faculty,
      speciality,
      semester,
      lectionHours,
      practiceHours,
      labHours,
      isExam)

  fun Assessment.toDto() = AssessmentDtoFromPostgre(
      id!!,
      discipline.id!!,
      score,
      date,
      teacherName,
      teacherId,
      studentName,
      studentId)

  companion object : KLogging()
}