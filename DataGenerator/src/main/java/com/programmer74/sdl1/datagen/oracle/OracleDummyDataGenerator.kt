package com.programmer74.sdl1.datagen.oracle

import com.programmer74.sdl1.oracle.repositories.TestRepository
import mu.KLogging
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile("oracle")
@Service
class OracleDummyDataGenerator(
  private val repository: TestRepository
) {

  init {
    logger.warn { "all OK" }
  }

  companion object : KLogging()
}