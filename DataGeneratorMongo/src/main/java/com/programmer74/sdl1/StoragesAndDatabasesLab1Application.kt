package com.programmer74.sdl1

import mu.KLogging
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class StoragesAndDatabasesLab1Application {
  companion object : KLogging() {
    @JvmStatic
    fun main(args: Array<String>) {
      SpringApplication.run(StoragesAndDatabasesLab1Application::class.java, *args)
    }
  }
}
