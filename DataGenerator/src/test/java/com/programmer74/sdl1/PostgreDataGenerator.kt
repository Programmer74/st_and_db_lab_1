package com.programmer74.sdl1

import mu.KLogging
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.test.context.ActiveProfiles

@SpringBootApplication
open class PostgreDataGenerator {
  companion object : KLogging() {
    @JvmStatic
    fun main(args: Array<String>) {
      SpringApplicationBuilder()
          .sources(StoragesAndDatabasesLab1Application::class.java)
          .profiles("postgre")
          .build()
          .run(*args)
    }
  }
}
