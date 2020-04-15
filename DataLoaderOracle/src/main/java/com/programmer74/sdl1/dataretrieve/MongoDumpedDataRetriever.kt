package com.programmer74.sdl1.dataretrieve

import com.fasterxml.jackson.module.kotlin.readValue
import com.programmer74.sdl1.dtos.DormitoryInhabitatDto
import com.programmer74.sdl1.dtos.DormitoryPersonDto
import org.springframework.stereotype.Service
import java.io.File

@Service
class MongoDumpedDataRetriever {

  private val srcPath = File("/home/hotaro/st_and_db_labs_dumps/mongo")

  fun getMongoDormitoryPersons(): List<DormitoryPersonDto> {
    val content = File(srcPath, "1__dormitory_persons.json").readText()
    return mapper.readValue(content)
  }

  fun getMongoDormitoryInhabitats(): List<DormitoryInhabitatDto> {
    val content = File(srcPath, "2__dormitory_inhabitats.json").readText()
    return mapper.readValue(content)
  }
}