package com.programmer74.sdl1

import mu.KLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
open class PostgreDataDumper {
  companion object : KLogging() {
    @JvmStatic
    fun main(args: Array<String>) {
      SpringApplicationBuilder()
          .sources(DataDumperApplication::class.java)
          .profiles("postgre")
          .build()
          .run(*args)
    }
  }
}
