package com.programmer74.sdl1.dtos

data class DisciplineDto(
  var id: Int,
  var universityName: String,
  var studyStandard: String,
  var disciplineName: String,
  var studyForm: String,
  var faculty: String,
  var speciality: String,
  var semester: Int,
  var lectionHours: Int,
  var practiceHours: Int,
  var labHours: Int,
  var isExam: Boolean
)