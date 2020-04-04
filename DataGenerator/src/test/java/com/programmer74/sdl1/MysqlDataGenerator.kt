package com.programmer74.sdl1

import mu.KLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
open class MysqlDataGenerator {
  companion object : KLogging() {
    @JvmStatic
    fun main(args: Array<String>) {
      SpringApplicationBuilder()
          .sources(StoragesAndDatabasesLab1Application::class.java)
          .profiles("mysql")
          .build()
          .run(*args)
    }
  }
}
