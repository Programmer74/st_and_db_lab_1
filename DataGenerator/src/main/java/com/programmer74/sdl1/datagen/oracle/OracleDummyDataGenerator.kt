package com.programmer74.sdl1.datagen.oracle

import com.programmer74.sdl1.mockdata.NameSurnameGenerator
import com.programmer74.sdl1.mockdata.UniversityGenerator
import com.programmer74.sdl1.mockdata.randomBool
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
import java.time.Instant
import javax.annotation.PostConstruct

@Profile("oracle")
@Service
class OracleDummyDataGenerator(
  private val personRepository: PersonRepository,
  private val studyGroupRepository: StudyGroupRepository,
  private val lessonEntryRepository: LessonEntryRepository,
  private val assessmentRepository: AssessmentRepository
) {

  @PostConstruct
  fun startGenerator() {
    if (personRepository.findAll().toList().isEmpty()) {
      logger.warn { "Beginning generating dummy data" }
      generateDummyData()
    } else {
      logger.error { "OracleDataGenerator had already done everything it was supposed to" }
      logger.warn { "Generated groups:" }
      studyGroupRepository.findAll().forEach { group ->
        logger.warn { " - $group" }
        group.participants.forEach {
          logger.warn { "   - $it" }
          it.assessments.forEach {
            logger.warn { "       - $it" }
          }
        }
      }
    }
  }

  private fun generateDummyData() {
    val randomPeople = generateAndSaveRandomPeople(100)
    val randomGroups = generateAndSaveRandomGroups(5)

    val peopleForGroup = randomPeople.toMutableSet()
    randomGroups.forEach { group ->
      val participants = (1 until 10).random()
      (0 until participants).map {
        val personToAdd = peopleForGroup.random()
        group.addParticipant(personToAdd)
        peopleForGroup.remove(personToAdd)
      }
      studyGroupRepository.save(group)
      generateAndSaveRandomLessonForGroup(randomPeople, group)
    }

    randomPeople.forEach {
      generateAndSaveRandomAssessmentForPerson(it)
    }
  }

  private fun generateAndSaveRandomPeople(count: Int) = (0 until count).map {
    val name = NameSurnameGenerator.getRandomName()
    Person(
        sid = NameSurnameGenerator.studentIdByName(name),
        name = name,
        birthDate = NameSurnameGenerator.generateRandomBirthDate(),
        birthPlace = NameSurnameGenerator.birthPlaces.random(),
        faculty = UniversityGenerator.facultyNames.random(),
        position = UniversityGenerator.positionNames.random(),
        isContractStudent = randomBool(),
        contractFrom = Instant.now(),
        contractTo = Instant.now()
    )
  }.map {
    personRepository.save(it)
  }

  private fun generateAndSaveRandomGroups(count: Int) = (0 until count).map {
    StudyGroup(
        name = UniversityGenerator.groupNames.random(),
        studyForm = UniversityGenerator.studyFormNames.random(),
        school = UniversityGenerator.schoolNames.random(),
        speciality = UniversityGenerator.specialityNames.random(),
        qualification = UniversityGenerator.qualificationNames.random(),
        studyYear = "2019/2020"
    )
  }.map {
    studyGroupRepository.save(it)
  }

  private fun generateAndSaveRandomLessonForGroup(
    teachersList: List<Person>,
    group: StudyGroup
  ) = lessonEntryRepository.save(
      LessonEntry(
          name = UniversityGenerator.groupNames.random(),
          teacher = teachersList.random(),
          weekday = (0 until 6).random(),
          hour = (8 until 16).random(),
          minute = (0..45 step 15).toList().random(),
          room = "Кронв " + (100 until 404).random(),
          studyGroup = group
      ))

  private fun generateAndSaveRandomAssessmentForPerson(
    person: Person
  ) = assessmentRepository.save(
      Assessment(
          name = UniversityGenerator.groupNames.random(),
          mark = 91,
          markLetter = "A",
          achievedAt = Instant.now(),
          achievedBy = person,
          disciplineName = UniversityGenerator.disciplineNames.random()
      ))

  companion object : KLogging()
}