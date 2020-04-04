package com.programmer74.sdl1.datagen.mysql

import com.programmer74.sdl1.mysql.repositories.TestRepository
import mu.KLogging
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile("mysql")
@Service
class MysqlDummyDataGenerator(
  private val testRepository: TestRepository
) {
  init {
    logger.warn { "all OK" }
  }

  companion object : KLogging()
}