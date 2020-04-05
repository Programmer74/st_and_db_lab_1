package com.programmer74.sdl1.datagen.mysql

import mu.KLogging
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile("mysql")
@Service
class MysqlDummyDataGenerator() {
  init {
    logger.warn { "all OK" }
  }

  companion object : KLogging()
}