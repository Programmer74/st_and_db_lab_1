package com.programmer74.sdl1.dtos

data class AssessmentDtoFromPostgre(
  var id: Int,
  var disciplineIdFromPostgre: Int,
  var score: Int,
  var date: Long,
  var teacherName: String,
  var teacherId: String,
  var studentName: String,
  var studentId: String
)