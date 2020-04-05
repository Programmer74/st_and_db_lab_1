package com.programmer74.sdl1.datagen.mysql

import com.programmer74.sdl1.mockdata.NameSurnameGenerator
import com.programmer74.sdl1.mockdata.UniversityGenerator
import com.programmer74.sdl1.mockdata.randomBool
import com.programmer74.sdl1.mysql.entities.*
import com.programmer74.sdl1.mysql.repositories.*
import mu.KLogging
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.time.Duration

@Suppress("SameParameterValue")
@Profile("mysql")
@Service
class MysqlDummyDataGenerator(
  private val personRepository: PersonRepository,
  private val conferenceRepository: ConferenceRepository,
  private val publicationRepository: PublicationRepository,
  private val projectRepository: ProjectRepository,
  private val bookTakenRepository: BookTakenRepository
) {
  init {
    if (personRepository.findAll().toList().isEmpty()) {
      logger.warn { "Beginning generating dummy data" }
      generateDummyData()
    } else {
      logger.error { "MysqlDataGenerator had already done everything it was supposed to" }
      logger.warn { "Generated persons:" }
      personRepository.findAll().forEach { person ->
        logger.warn { " - $person" }
        person.conferences.logWarn(2)
        person.publications.logWarn(2)
        person.projects.logWarn(2)
        person.booksTaken.logWarn(2)
      }
    }
  }

  private fun Set<Any>.logWarn(padLeft: Int) {
    this.forEach {
      logger.warn { "${"".padStart(padLeft, ' ')} - $it" }
    }
  }

  private fun generateDummyData() {
    val randomPeople = generateAndSaveRandomPeople(20)
    val randomConferences = generateAndSaveRandomConferences(5)
    val randomPublications = generateAndSaveRandomPublications(5)
    val randomProjects = generateAndSaveRandomProjects(5)

    val randomBooks = generateAndFillRandomBooks(10, randomPeople)

    randomConferences.forEach { conference ->
      val participants = (1 until 10).random()
      (0 until participants).map {
        conference.addParticipant(randomPeople.random())
      }
      conferenceRepository.save(conference)
    }

    randomPublications.forEach { publication ->
      val authors = (1 until 3).random()
      (0 until authors).map {
        publication.addAuthor(randomPeople.random())
      }
      publicationRepository.save(publication)
    }

    randomProjects.forEach { project ->
      val workers = (1 until 5).random()
      (0 until workers).map {
        project.addWorker(randomPeople.random())
      }
      projectRepository.save(project)
    }
  }

  private fun generateAndSaveRandomPeople(count: Int) = (0 until count).map {
    Person(
        name = NameSurnameGenerator.getRandomName(),
        position = UniversityGenerator.positionNames.random()
    )
  }.map {
    personRepository.save(it)
  }

  private fun generateAndSaveRandomConferences(count: Int) = (0 until count).map {
    Conference(
        name = "${UniversityGenerator.conferenceNames.random()} №$it",
        place = UniversityGenerator.universityNames.random(),
        date = System.currentTimeMillis()
    )
  }.map {
    conferenceRepository.save(it)
  }

  private fun generateAndSaveRandomPublications(count: Int) = (0 until count).map {
    Publication(
        name = "Какая-то публикация №$it",
        type = UniversityGenerator.publicationTypes.random(),
        language = UniversityGenerator.languages.random(),
        source = UniversityGenerator.sources.random(),
        pages = (1 until 15).random(),
        sourceType = UniversityGenerator.sourceTypes.random(),
        quoteIndex = (0 until 5).random(),
        date = System.currentTimeMillis()
    )
  }.map {
    publicationRepository.save(it)
  }

  private fun generateAndSaveRandomProjects(count: Int) = (0 until count).map {
    Project(
        name = "Какой-то проект №$it",
        dateFrom = System.currentTimeMillis(),
        dateTo = System.currentTimeMillis() + Duration.ofDays(1).toMillis()
    )
  }.map {
    projectRepository.save(it)
  }

  private fun generateAndFillRandomBooks(count: Int, people: List<Person>) = (0 until count).map {
    BookTaken(
        bookName = "Книга $it",
        takenBy = people.random(),
        takenAt = System.currentTimeMillis() - Duration.ofDays(1).toMillis(),
        returnedAt = if (randomBool()) System.currentTimeMillis() else 0L
    )
  }.map {
    bookTakenRepository.save(it)
  }

  companion object : KLogging()
}