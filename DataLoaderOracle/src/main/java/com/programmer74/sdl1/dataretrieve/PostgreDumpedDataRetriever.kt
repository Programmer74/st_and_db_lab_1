package com.programmer74.sdl1.dataretrieve

import com.fasterxml.jackson.module.kotlin.readValue
import com.programmer74.sdl1.dtos.AssessmentDtoFromPostgre
import com.programmer74.sdl1.dtos.DisciplineDto
import org.springframework.stereotype.Service
import java.io.File

@Service
class PostgreDumpedDataRetriever {

  private val srcPath = File("/home/hotaro/st_and_db_labs_dumps/postgre")

  fun getPostgreDisciplines(): List<DisciplineDto> {
    val content = File(srcPath, "1__disciplines.json").readText()
    return mapper.readValue(content)
  }

  fun getPostgreAssessments(): List<AssessmentDtoFromPostgre> {
    val content = File(srcPath, "2__assessments.json").readText()
    return mapper.readValue(content)
  }
}