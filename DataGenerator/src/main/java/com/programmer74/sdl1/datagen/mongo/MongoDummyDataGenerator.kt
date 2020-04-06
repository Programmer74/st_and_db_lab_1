package com.programmer74.sdl1.datagen.mongo

import com.programmer74.sdl1.mongo.entities.Test
import com.programmer74.sdl1.mongo.repositories.TestRepository
import mu.KLogging
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class MongoDummyDataGenerator(
  private val testRepository: TestRepository
) {

  @PostConstruct
  fun runGenerator() {
    if (testRepository.findAll().size == 0) {
      testRepository.save(Test(1L, 2L))
    }
    logger.warn { "all OK" }
    testRepository.findAll().forEach { logger.warn { " - $it" } }
  }

  companion object : KLogging()

}