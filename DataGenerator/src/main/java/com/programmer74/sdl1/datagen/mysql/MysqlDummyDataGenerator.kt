package com.programmer74.sdl1.datagen.mysql

import com.programmer74.sdl1.mockdata.NameSurnameGenerator
import com.programmer74.sdl1.mockdata.UniversityGenerator
import com.programmer74.sdl1.mysql.entities.Conference
import com.programmer74.sdl1.mysql.entities.Person
import com.programmer74.sdl1.mysql.repositories.ConferenceRepository
import com.programmer74.sdl1.mysql.repositories.PersonRepository
import mu.KLogging
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile("mysql")
@Service
class MysqlDummyDataGenerator(
  private val personRepository: PersonRepository,
  private val conferenceRepository: ConferenceRepository
) {
  init {
    if (personRepository.findAll().toList().isEmpty()) {
      logger.warn { "Beginning generating dummy data" }
      generateDummyData()
    } else {
      val conferenceRetrieved = conferenceRepository.findAll().first()
      val personRetrieved = personRepository.findAll().first()
      assert(conferenceRetrieved.participants.first() == personRetrieved)
      assert(personRetrieved.conferences.first() == conferenceRetrieved)
      throw IllegalStateException("MysqlDataGenerator had already done everything it was supposed to")
    }
  }

  private fun generateDummyData() {
    val person = Person(
        name = NameSurnameGenerator.getRandomName(),
        position = UniversityGenerator.positionNames.random()
    )
    personRepository.save(person)
    val conference = Conference(
        name = UniversityGenerator.conferenceNames.random(),
        place = UniversityGenerator.universityNames.random(),
        date = System.currentTimeMillis()
    )
    conferenceRepository.save(conference)

    conference.addParticipant(person)
    conferenceRepository.save(conference)
  }

  companion object : KLogging()
}