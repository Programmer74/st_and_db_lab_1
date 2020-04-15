package com.programmer74.sdl1.dataretrieve

import com.fasterxml.jackson.module.kotlin.readValue
import com.programmer74.sdl1.dtos.AssessmentDtoFromOracle
import com.programmer74.sdl1.dtos.LessonEntryDto
import com.programmer74.sdl1.dtos.PersonDtoFromOracle
import com.programmer74.sdl1.dtos.StudyGroupDto
import org.springframework.stereotype.Service
import java.io.File

@Service
class OracleDumpedDataRetriever {

  private val srcPath = File("/home/hotaro/st_and_db_labs_dumps/oracle")

  fun getOraclePersons(): List<PersonDtoFromOracle> {
    val content = File(srcPath, "1__persons.json").readText()
    return mapper.readValue(content)
  }

  fun getOracleLessonEntries(): List<LessonEntryDto> {
    val content = File(srcPath, "2__lesson_entries.json").readText()
    return mapper.readValue(content)
  }

  fun getOracleStudyGroups(): List<StudyGroupDto> {
    val content = File(srcPath, "3__study_groups.json").readText()
    return mapper.readValue(content)
  }

  fun getOracleAssessments(): List<AssessmentDtoFromOracle> {
    val content = File(srcPath, "4__assessments.json").readText()
    return mapper.readValue(content)
  }
}