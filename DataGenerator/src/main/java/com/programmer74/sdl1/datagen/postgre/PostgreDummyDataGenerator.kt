package com.programmer74.sdl1.datagen.postgre

import com.programmer74.sdl1.mockdata.NameSurnameGenerator
import com.programmer74.sdl1.mockdata.UniversityGenerator
import com.programmer74.sdl1.mockdata.random
import com.programmer74.sdl1.mockdata.randomBool
import com.programmer74.sdl1.postgre.entities.Assessment
import com.programmer74.sdl1.postgre.entities.Discipline
import com.programmer74.sdl1.postgre.repositories.AssessmentRepository
import com.programmer74.sdl1.postgre.repositories.DisciplineRepository
import mu.KLogging
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import kotlin.math.abs

@Profile("postgre")
@Service
class PostgreDummyDataGenerator(
  private val disciplineRepository: DisciplineRepository,
  private val assessmentRepository: AssessmentRepository
) {

  init {
    if (disciplineRepository.findAll().toList().isEmpty()) {
      logger.warn { "Beginning generating dummy data" }
      generateDummyData()
    } else {
      throw IllegalStateException("PostgreDataGenerator had already done everything it was supposed to")
    }
  }

  private fun generateDummyData() {
    val disciplines = generateDummyDisciplines()
    (0 until 20).map {
      val discipline = disciplines.random()
      Assessment(
          null,
          discipline,
          UniversityGenerator.getRandomScore(),
          System.currentTimeMillis(),
          discipline.teacherName(),
          0,
          NameSurnameGenerator.getRandomName(),
          0
      )
    }.forEach { assessmentRepository.save(it) }
  }

  private fun generateDummyDisciplines(): List<Discipline> {
    return (0 until 1).map {
      Discipline(
          null,
          UniversityGenerator.universityNames.random(),
          UniversityGenerator.studyStandardNames.random(),
          UniversityGenerator.disciplineNames.random(),
          UniversityGenerator.studyFormNames.random(),
          UniversityGenerator.facultyNames.random(),
          UniversityGenerator.specialityNames.random(),
          UniversityGenerator.getRandomSemesterNumber(),
          UniversityGenerator.getRandomHours(),
          UniversityGenerator.getRandomHours(),
          UniversityGenerator.getRandomHours(),
          randomBool()
      )
    }.map { disciplineRepository.save(it) }
  }

  fun List<Discipline>.random() = this[(0 until this.size).random()]

  fun Discipline.teacherName() =
      NameSurnameGenerator.getRandomName(abs(this.hashCode() * 1.0 / Int.MAX_VALUE))

  companion object : KLogging()
}