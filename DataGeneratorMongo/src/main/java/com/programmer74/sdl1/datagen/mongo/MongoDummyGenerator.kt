package com.programmer74.sdl1.datagen.mongo

import com.programmer74.sdl1.datagen.mongo.repositories.DormitoryInhabitatRepository
import com.programmer74.sdl1.datagen.mongo.repositories.DormitoryPersonRepository
import com.programmer74.sdl1.mockdata.NameSurnameGenerator
import com.programmer74.sdl1.mockdata.UniversityGenerator
import com.programmer74.sdl1.mockdata.randomBool
import com.programmer74.sdl1.mongo.entities.Dormitory
import com.programmer74.sdl1.mongo.entities.DormitoryInhabitat
import com.programmer74.sdl1.mongo.entities.DormitoryPerson
import com.programmer74.sdl1.mongo.entities.DormitoryRoom
import mu.KLogging
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import javax.annotation.PostConstruct

@Service
class MongoDummyDataGenerator(
  private val dormitoryPersonRepository: DormitoryPersonRepository,
  private val dormitoryInhabitatRepository: DormitoryInhabitatRepository
) {

  @PostConstruct
  fun runGenerator() {
    if (dormitoryInhabitatRepository.findAll().isEmpty()) {
      logger.warn { "Beginning generating dummy data" }
      generateRandomData()
    } else {
      logger.error { "MongoDataGenerator had already done everything it was supposed to" }
      logger.warn { "Generated inhabitats:" }
      dormitoryInhabitatRepository.findAll().forEach { logger.warn { " - $it" } }
    }
  }

  fun generateRandomData() {
    val dormitories = generateRandomDormitories(2)
    val randomPeople = generateAndSaveRandomPeople(15)
    val dormRooms = generateRandomDormRooms(10, dormitories)
    randomPeople.map {
      DormitoryInhabitat(
          person = randomPeople.random(),
          price = 1480,
          latestAction = "вышел",
          latestActionAt = Instant.now(),
          livesFrom = Instant.now().minusMillis(Duration.ofDays(365).toMillis()),
          livesTo = Instant.now().plusMillis(Duration.ofDays(365).toMillis()),
          livesAt = dormRooms.random()
      )
    }.map { dormitoryInhabitatRepository.save(it) }
  }

  private fun generateAndSaveRandomPeople(count: Int) = (0 until count).map {
    val randomName = NameSurnameGenerator.getRandomName()
    DormitoryPerson(
        id = NameSurnameGenerator.studentIdByName(randomName),
        name = randomName,
        isContractStudent = randomBool(),
        isBeneficiary = randomBool()
    )
  }.map {
    dormitoryPersonRepository.save(it)
  }

  private fun generateRandomDormitories(count: Int) = (0 until count).map {
    Dormitory(
        address = UniversityGenerator.dormitories.random(),
        totalRooms = (100 until 200).random()
    )
  }

  private fun generateRandomDormRooms(count: Int, dorms: List<Dormitory>) = (0 until count).map {
    DormitoryRoom(
        dormitory = dorms.random(),
        roomNo = (0 until 100).random(),
        maxInhabitats = 5,
        latestDesinfection = Instant.EPOCH,
        insects = true,
        warnings = 0
    )
  }

  companion object : KLogging()

}