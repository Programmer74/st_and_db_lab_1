package com.programmer74.sdl1.datagen.mysql

import com.programmer74.sdl1.mockdata.NameSurnameGenerator
import com.programmer74.sdl1.mockdata.UniversityGenerator
import com.programmer74.sdl1.mysql.entities.*
import com.programmer74.sdl1.mysql.repositories.*
import mu.KLogging
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.util.*

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
      val conferenceRetrieved = conferenceRepository.findAll().first()
      val publicationRetrieved = publicationRepository.findAll().first()
      val projectRetrieved = projectRepository.findAll().first()
      val personRetrieved = personRepository.findAll().first()
      val bookTakenRetrieved = bookTakenRepository.findAll().first()
      assert(conferenceRetrieved.participants.first() == personRetrieved)
      assert(personRetrieved.conferences.first() == conferenceRetrieved)
      assert(publicationRetrieved.authors.first() == personRetrieved)
      assert(personRetrieved.publications.first() == publicationRetrieved)
      assert(projectRetrieved.workers.first() == personRetrieved)
      assert(personRetrieved.projects.first() == projectRetrieved)
      assert(bookTakenRetrieved.takenBy == personRetrieved)
      assert(personRetrieved.booksTaken.first() == bookTakenRetrieved)
      throw IllegalStateException("MysqlDataGenerator had already done everything it was supposed to")
    }
  }

  private fun generateDummyData() {
    val person = personRepository.save(
        Person(
            name = NameSurnameGenerator.getRandomName(),
            position = UniversityGenerator.positionNames.random()
        ))

    val conference = conferenceRepository.save(
        Conference(
            name = UniversityGenerator.conferenceNames.random(),
            place = UniversityGenerator.universityNames.random(),
            date = System.currentTimeMillis()
        ))

    val project = projectRepository.save(
        Project(
            name = UUID.randomUUID().toString(),
            dateFrom = System.currentTimeMillis(),
            dateTo = System.currentTimeMillis()
        ))

    val publication = publicationRepository.save(
        Publication(
            name = UUID.randomUUID().toString(),
            type = UniversityGenerator.publicationTypes.random(),
            language = UniversityGenerator.languages.random(),
            source = UniversityGenerator.sources.random(),
            pages = (1 until 15).random(),
            sourceType = UniversityGenerator.sourceTypes.random(),
            quoteIndex = (0 until 5).random(),
            date = System.currentTimeMillis()
        ))

    val book = bookTakenRepository.save(
        BookTaken(
            bookName = UUID.randomUUID().toString(),
            takenBy = person,
            takenAt = System.currentTimeMillis(),
            returnedAt = 0L
        )
    )

    conference.addParticipant(person)
    conferenceRepository.save(conference)

    project.addWorker(person)
    projectRepository.save(project)

    publication.addAuthor(person)
    publicationRepository.save(publication)
  }

  companion object : KLogging()
}