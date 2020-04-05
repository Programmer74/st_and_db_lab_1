package com.programmer74.sdl1.datagen.oracle

import com.programmer74.sdl1.oracle.repositories.AssessmentRepository
import com.programmer74.sdl1.oracle.repositories.LessonEntryRepository
import com.programmer74.sdl1.oracle.repositories.PersonRepository
import com.programmer74.sdl1.oracle.repositories.StudyGroupRepository
import mu.KLogging
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile("oracle")
@Service
class OracleDummyDataGenerator(
  private val personRepository: PersonRepository,
  private val studyGroupRepository: StudyGroupRepository,
  private val lessonEntryRepository: LessonEntryRepository,
  private val assessmentRepository: AssessmentRepository
) {

  init {
    logger.warn { "all OK" }
  }

  companion object : KLogging()
}